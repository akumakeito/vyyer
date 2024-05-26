package com.akumakeito.vyyer.presentation.scanhistory

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akumakeito.vyyer.R
import com.akumakeito.vyyer.databinding.ItemScanBinding
import com.akumakeito.vyyer.domain.model.Gender
import com.akumakeito.vyyer.domain.model.IdentityScanInfoModel
import com.akumakeito.vyyer.presentation.util.convertDateFormat
import com.bumptech.glide.Glide
import dagger.hilt.android.qualifiers.ApplicationContext

class IdentityScanInfoAdapter(
    @ApplicationContext private val context : Context
) : PagingDataAdapter<IdentityScanInfoModel, IdentityScanInfoViewHolder>(ItemDiffCallback()) {

    override fun onBindViewHolder(holder: IdentityScanInfoViewHolder, position: Int) {
        Log.d("ScanRemoteMediator", "position : $position")

        val item = getItem(position) ?: return
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdentityScanInfoViewHolder {
        val binding = ItemScanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IdentityScanInfoViewHolder(binding, context)
    }

}

class IdentityScanInfoViewHolder(
    private val binding : ItemScanBinding,
    @ApplicationContext private val context : Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : IdentityScanInfoModel) {

        Log.d("ScanRemoteMediator", "scan item id : ${item.id} name : ${item.fullName} time : ${item.createdAt}")

        binding.apply{
            tvUserNameSurname.text = item.fullName
            tvScanInfo.text = context.getString(R.string.scan_info, convertDateFormat(item.createdAt), item.verdictName)
            Glide.with(ivUserPhoto)
                .load("")
                .centerCrop()
                .placeholder( if(item.gender==Gender.FEMALE) R.drawable.female_user_placeholder else R.drawable.male_user_placeholder )
                .into(ivUserPhoto)
        }

    }

}

class ItemDiffCallback : DiffUtil.ItemCallback<IdentityScanInfoModel>() {
    override fun areItemsTheSame(
        oldItem: IdentityScanInfoModel,
        newItem: IdentityScanInfoModel
    ): Boolean {
        if(oldItem::class != newItem::class){
            return false
        }

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: IdentityScanInfoModel,
        newItem: IdentityScanInfoModel
    ): Boolean {
        return oldItem == newItem
    }

}

