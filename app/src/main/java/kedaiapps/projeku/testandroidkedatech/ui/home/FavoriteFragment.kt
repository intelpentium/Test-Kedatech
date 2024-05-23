package kedaiapps.projeku.testandroidkedatech.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import kedaiapps.projeku.testandroidkedatech.databinding.FragmentFavoriteBinding
import kedaiapps.projeku.testandroidkedatech.db.table.FavoriteTable
import kedaiapps.projeku.testandroidkedatech.ext.observe
import kedaiapps.projeku.testandroidkedatech.modules.base.BaseFragment
import kedaiapps.projeku.testandroidkedatech.ui.home.adapter.AdapterFavorite
import kedaiapps.projeku.testandroidkedatech.viewmodel.RepoViewModel
import kedaiapps.projeku.testandroidkedatech.ext.hideKeyboard

class FavoriteFragment : BaseFragment() {
    lateinit var mBinding: FragmentFavoriteBinding
    private val viewModel by viewModels<RepoViewModel>()

    private val adapter by lazy(LazyThreadSafetyMode.NONE){
        AdapterFavorite(::onClick)
    }

    private var list: List<FavoriteTable> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        handleState()
    }

    private fun initView() {
        mBinding.search.addTextChangedListener {
            val filteredModelList: List<FavoriteTable> = filter(list, it.toString())
            adapter.clearData()
            adapter.insertData(filteredModelList)
        }

        mBinding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            false
        }

        mBinding.rv.adapter = adapter
    }

    private fun filter(
        models: List<FavoriteTable>,
        query: String
    ): List<FavoriteTable> {

        val filteredModelList: MutableList<FavoriteTable> = ArrayList()
        for (model in models) {
            val name: String = model.name.toLowerCase()
            if (name.contains(query.toLowerCase())) {
                filteredModelList.add(model)
            }
        }
        return filteredModelList
    }

    private fun handleState() {
        observe(viewModel.getFavorite()){
            if(it != null){
                adapter.clearData()
                adapter.insertData(it)

                list = it
            }
        }
    }

    private fun onClick(data: FavoriteTable) {
        val intent = Intent(requireActivity(), HomeDetailActivity::class.java)
        intent.putExtra("id", data.fav_id)
        startActivity(intent)
    }
}