package com.example.economics.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.economics.R;
import com.example.economics.adapters.recycler_views.Econ_RecylerViewAdapter;
import com.example.economics.models.EconIndicatorModel;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link leftFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class leftFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //recyclerview model setup
    ArrayList<EconIndicatorModel> econIndicatorModel = new ArrayList<>();
    RecyclerView recyclerView;



    int[] econIndicatorImages = {R.drawable.ic_fed_funds_rate, R.drawable.ic_gdp, R.drawable.ic_inflation,
            R.drawable.ic_yields, R.drawable.ic_unemployment, R.drawable.ic_durable};



    public leftFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment leftFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static leftFragment newInstance(String param1, String param2) {
        leftFragment fragment = new leftFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_left, container, false);
    }


    //WHERE WE PUT RECYCLERVIEW CODE
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //main recycler view. Set up recycler adapter after models are set up!
        //getView has to be used to use findViewById. A quirk of this onViewCreated. Returns the root view of the fragment from onCreateView
        recyclerView = view.findViewById(R.id.econRecyclerView);

        //sets up models
        setUpEconIndicatorModels();

        Econ_RecylerViewAdapter adapter = new Econ_RecylerViewAdapter(getContext(), econIndicatorModel );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Drag and drop functionality
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView((recyclerView));

    }

    //models
    private void setUpEconIndicatorModels(){


        String[] econIndicatorNames = getResources().getStringArray(R.array.econ_indicators_titles);
        for (int i = 0; i < econIndicatorNames.length; i++){
            econIndicatorModel.add(new EconIndicatorModel(econIndicatorNames[i], econIndicatorImages[i] ));
        }
    }

    //drag and drop
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN |
            ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(econIndicatorModel, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

}