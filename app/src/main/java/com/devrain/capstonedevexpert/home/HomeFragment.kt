package com.devrain.capstonedevexpert.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.devrain.capstonedevexpert.R
import com.devrain.capstonedevexpert.core.data.source.Resource
import com.devrain.capstonedevexpert.core.ui.MovieAdapter
import com.devrain.capstonedevexpert.databinding.FragmentHomeBinding
import com.devrain.capstonedevexpert.detail.DetailMovieActivity
import com.devrain.capstonedevexpert.favorite.FavoriteFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    //    private lateinit var homeViewModel: HomeViewModel
    private val homeViewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val tourismAdapter = MovieAdapter()
            tourismAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
                Log.d("HOMEFRAGMENT", selectedData.toString())
            }

//            val factory = ViewModelFactory.getInstance(requireActivity())
//            homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

            homeViewModel.movie.observe(viewLifecycleOwner, { tourism ->
                if (tourism != null) {
                    when (tourism) {
                        is com.devrain.capstonedevexpert.core.data.source.Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is com.devrain.capstonedevexpert.core.data.source.Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            tourismAdapter.setData(tourism.data)
                        }
                        is com.devrain.capstonedevexpert.core.data.source.Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text =
                                tourism.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            })

            with(binding.rvMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tourismAdapter
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.option_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.menu_favorite -> {
//                val mCategoryFragment = FavoriteFragment()
//                val mFragmentManager = fragmentManager
//                mFragmentManager?.beginTransaction()?.apply {
//                    replace(R.id.nav_host_fragment, mCategoryFragment, FavoriteFragment::class.java.simpleName)
//                    addToBackStack(null)
//                    commit()
//                }
//                return true
//            }
//            else -> return true
//        }
//        return true
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}