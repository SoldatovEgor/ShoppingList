package ru.soldatov.android.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.soldatov.android.shoppinglist.R
import ru.soldatov.android.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var buttonAddShopItem: FloatingActionButton

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterShopList: ShopListAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this) {
            adapterShopList.submitList(it)
        }
        binding.buttonAddShopItem.setOnClickListener {
            if (isContainerNotNull()) {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            } else {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            }
        }
    }

    private fun isContainerNotNull(): Boolean {
        return binding.shopItemContainer != null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        adapterShopList = ShopListAdapter()
        with(binding.rvShopList) {
            adapter = adapterShopList
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ENABLED,
                ShopListAdapter.MAX_POOL_RV
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.DISABLED,
                ShopListAdapter.MAX_POOL_RV
            )
        }
        setupLongClickListener()
        setupClickListener()

        setupSwipeListener(binding.rvShopList)
    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapterShopList.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
                Toast.makeText(
                    this@MainActivity,
                    "${item.name} was be delete",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        val itemTouchCallback = ItemTouchHelper(callback)
        itemTouchCallback.attachToRecyclerView(rvShopList)
    }

    private fun setupClickListener() {
        adapterShopList.shopItemClickListener = {
            if (isContainerNotNull()) {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            } else {
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            }

        }
    }

    private fun setupLongClickListener() {
        adapterShopList.shopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    override fun onEditingFinished() {
        supportFragmentManager.popBackStack()
        Toast.makeText(this, "Cusses", Toast.LENGTH_SHORT).show()
    }

}