package com.example.wordapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WordsFragment extends Fragment {

    private RecyclerView recyclerView;
    private WordViewModel viewModel;
    private MyAdapter adapter;
    private FloatingActionButton floatingActionButton;
    private List<WordEntity> tempList;


    public WordsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_words, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);

        //初始化
        recyclerView = requireActivity().findViewById(R.id.recycle);
        viewModel = new ViewModelProvider(this).get(WordViewModel.class);
        floatingActionButton  = requireActivity().findViewById(R.id.floatingActionButton);

        //很重要的recycleview 添加数据 livedata
        adapter = new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(adapter);

        viewModel.getLiveDataAllWords().observe(requireActivity(), new Observer<List<WordEntity>>() {
            @Override
            public void onChanged(List<WordEntity> wordEntities) {
                tempList = wordEntities;
                adapter.setWordsList(wordEntities);
                adapter.notifyDataSetChanged();
            }
        });

        //加号添加点击事件
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_wordsFragment_to_addFragment);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final WordEntity deleteWord = tempList.get(viewHolder.getAdapterPosition());
                viewModel.deleteWords(deleteWord);
                Snackbar.make(requireActivity().findViewById(R.id.coordFragment),"删除了一个词汇",Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewModel.insertWords(deleteWord);
                            }
                        }).show();
            }
        }).attachToRecyclerView(recyclerView);

    }


    //当重新回到这个页面以后。执行onResume()
    @Override
    public void onResume() {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(),0);
        super.onResume();
    }


    //Menu的初始化
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main_menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setMaxWidth(800);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String pattern = newText.trim();
                List<WordEntity> entityList = viewModel.findWordWithParttern(pattern);
                tempList = entityList;
                adapter.setWordsList(entityList);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.clearData:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("清空所有数据");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.clearWords();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //什么都不做。
                    }
                });
                builder.create();
                builder.show();
                break;

            case R.id.inserttext:
                String[] english = {
                    "Hello",
                    "World",
                    "Android",
                    "Google",
                    "Studio",
                    "Project",
                    "Database",
                    "Recycler",
                    "View",
                    "String",
                    "Value",
                    "Integer"
            };
                String[] chinese = {
                    "你好",
                    "世界",
                    "安卓系统",
                    "谷歌公司",
                    "工作室",
                    "项目",
                    "数据库",
                    "回收站",
                    "视图",
                    "字符串",
                    "价值",
                    "整数类型"
            };
                for(int i=0;i<english.length;i++){
                    WordEntity wordEntity = new WordEntity(english[i],chinese[i]);
                    viewModel.insertWords(wordEntity);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
