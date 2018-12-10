package com.rawggar.deltechmobile.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.rawggar.deltechmobile.Adapters.MessMenuDialogAdapter;
import com.rawggar.deltechmobile.R;

public class MessMenuDialogFragment extends DialogFragment {
    private static final String TAG = "MessMenuDialogFragment";
    public String Finarray[][][][]= {

            //MONDAY
                    {{{"CVR", "Sambhar-vada, Idli, Upma, Omelette, Toast, Bread-jam, Boiled-egg, Tea, Milk", "30"},     {"VVS", "Sambhar-vada, Idli, Upma, Omelette, Toast, Bread-jam, Boiled-egg, Tea, Milk", "30"},   {"ABH", "Sambar-vada, Idli, Upma, Toast, Tea, Milk", "30"},                     {"HJB", "Sambhar-vada, Upma, Toast, Tea, Milk", "30"},                      {"SNH", "Sambhar-vada, Idli, Upma, Taost, Tea, Milk", "30"}},
                    {{"CVR", "Rajma, Aloo-fry, Roti, Raita, Rice", "40"},                                               {"VVS", "Rajma, Aloo-fry, Roti, Raita, Rice", "40"},                                            {"ABH", "Rice, Rajma, Aloo-fry, Roti, Raita", "40"},                            {"HJB", "Rice, Aloo-Padvar, Dal, Roti, Raita", "40"},                       {"SNH", "Rice, Rajma, Aloo-fry, Roti, Raita", "40"}},
                    {{"CVR", "Bread-pakoda, Tea", "15"},                                                                {"VVS", "Bread-pakoda, Tea", "15"},                                                             {"ABH", "Poha, Tea", "15"},                                                     {"HJB", "Bread-pakoda, Tea", "15"},                                         {"SNH", "Poha, Tea", "15"}},
                    {{"CVR", "Malai-kofta/Egg-curry, Arhar, Sevaiyan, Rice, Roti", "50"},                               {"VVS", "Malai-kofta/Egg-curry, Arhar, Sevaiyan, Rice, Roti", "50"},                            {"ABH", "Malai-kofta/Egg-curry, Rice, Dal, Kheer, Roti", "50"},                 {"HJB", "Malai-kofta/Egg-curry, Roti, Sevaiyan, Rice, Dal", "50"},          {"SNH", "Malai-kofta/Egg-curry, Roti, Kheer, Rice, Dal", "50"}}},

            //TUESDAY
                    {{{"CVR", "Parantha, Dahi, Toast, Bread-jam, Tea, Milk", "30"},                                     {"VVS", "Parantha, Dahi, Toast, Bread-jam, Tea, Milk", "30"},                                   {"ABH", "Plain-parantha, Tea, Milk", "30"},                                     {"HJB", "Plain-parantha, Tea, Milk", "30"},                                 {"SNH", "Plian-parantha, Tea, Milk", "30"}},
                    {{"CVR", "Kadi-pakoda, Pattagobhi, Rice, Roti", "40"},                                              {"VVS", "Kadi-pakoda, Pattagobhi, Rice, Roti", "40"},                                           {"ABH", "Kadi-pakoda, Rice, Aloo-pattagobhi, Raita, Roti", "40"},               {"HJB", "Rice, Kadi-pakode, Aloo-pattagobhi, Roti, Raita", "40"},           {"SNH", "Rice, Kadi-pakore, Aloo-pattagobhi, Roti, Raita", "40"}},
                    {{"CVR", "Macaroni, Tea", "15"},                                                                    {"VVS", "Macaroni, Tea", "15"},                                                                 {"ABH", "Macaroni, Tea", "15"},                                                 {"HJB", "Pasta, Tea", "15"},                                                {"SNH", "Macaroni, Tea", "15"}},
                    {{"CVR", "Shahi-paneer, Poori, Chole, Pulao-rice, Roti, Custard", "60"},                            {"VVS", "Shahi-paneer, Poori, Chole, Pulao-rice, Roti, Custard", "60"},                         {"ABH", "Shahi-paneer, Chole, Custard, Poori, Pulao-rice", "60"},               {"HJB", "Shahi-paneer, Chole, Poori, Custard, Pulao-rice", "60"},           {"SNH", "Shai-paneer, Chole, Poori, Custard, Pulao-rice", "60"}}},

            //WEDNESDAY
                    {{{"CVR", "Pao-bhaji, Omelette, Bread-jam, Boiled-egg, Tea, Milk", "30"},                           {"VVS", "Pao-bhaji, Omelette, Bread-jam, Boiled-egg, Tea, Milk", "30"},                         {"ABH", "Pao-bhaji, Chole-kulche, Toast, Tea, Milk", "30"},                     {"HJB", "Dosa, Omelette, Toast, Tea, Milk", "30"},                          {"SNH", "Pao-bhaji, Chole-kulche, Toast, Tea, Milk", "30"}},
                    {{"CVR", "Chana-dal, Shimla-Mirch, Rice, Roti, Aloo, Raita, Chowmein", "40"},                       {"VVS", "Chana-dal, Shimla-Mirch, Rice, Roti, Aloo, Raita, Chowmein", "40"},                    {"ABH", "Dal-Makhani, Rice, Sabji, Roti, Raita", "40"},                         {"HJB", "Rice, Dal-makhani, Sabji, Roti, Raita", "40"},                     {"SNH", "Rice, Dal-makhani, Sabji, Roti, Raita", "40"}},
                    {{"CVR", "Pastry, Coffee", "15"},                                                                   {"VVS", "Pastry, Coffee", "15"},                                                                {"ABH", "Pastry, Coffee", "15"},                                                {"HJB", "Sandwich-bread, Tea", "15"},                                       {"SNH", "Pastry,Coffee", "15"}},
                    {{"CVR", "Matar-paneer/Chicken-curry, Lal-Masoor, Rice, Roti, Halwa", "50/80"},                     {"VVS", "Matar-paneer/Chicken-curry, Lal-Masoor, Rice, Roti, Halwa", "50/80"},                  {"ABH", "Matar-paneer/Chicken-curry, Yellow-dal, Rice, Roti", "50/80"},         {"HJB", "Matar-panner, Halwa, Roti, Rice, Dal", "50"},                      {"SNH", "Matar-paneer/Chicken-curry, Yellow-dal, Roti, Rice", "50/80"}}},

            //THURSDAY
                    {{{"CVR", "Chole-bhature, Omelette, Toast, Bread-jam, Boiled-egg, Tea, Milk", "30"},                {"VVS", "Chole-bhature, Omelette, Toast, Bread-jam, Boiled-egg, Tea, Milk", "30"},              {"ABH", "Sambhar-dosa, Omelette, Toast, Tea, Milk", "30"},                      {"HJB", "Chole-bhature, Tea, Milk", "30"},                                  {"SNH", "Sambhar-dosa, Omelette, Toast, Tea, Milk", "30"}},
                    {{"CVR", "Kale-chole, Phool-gobhi, Rice, Roti, Raita", "40"},                                       {"VVS", "Kale-chole, Phool-gobhi, Rice, Roti, Raita", "40"},                                    {"ABH", "Arhar-dal, Rice, Sabji, Roti, Raita", "40"},                           {"HJB", "Rice, Rajma, Aloo-fry, Roti, Raita", "40"},                        {"SNH", "Arhar, Rice, Sabji, Roti, Raita", "40"}},
                    {{"CVR", "Burger, Tea", "15"},                                                                      {"VVS", "Burger, Tea", "15"},                                                                   {"ABH", "Bread-pakoda, Tea", "15"},                                             {"HJB", "Aloo-Bonda, Tea", "15"},                                           {"SNH", "Bread-pakoda, Tea", "15"}},
                    {{"CVR", "Aloo-Soyabean, Dal-Makhani, Gulab-jamun, Rice, Roti", "40"},                              {"VVS", "Aloo-Soyabean, Dal-Makhani, Gulab-jamun, Rice, Roti", "40"},                           {"ABH", "Mix-veg, Gulab-jamun, Roti, Rice, Dal", "40"},                         {"HJB", "Mix-veg, Roti, Gulab-jamun, Rice, Dal", "40"},                     {"SNH", "Mix-veg, Gulab-jamun, Roti, Rice, Dal", "40"}}},

            //FRIDAY
                    {{{"CVR", "Sambhar-dosa, Omelette, Toast, Bread-jam, Boiled-egg, Tea, Milk", "30"},                 {"VVS", "Sambhar-dosa, Omelette, Toast, Bread-jam, Boiled-egg, Tea, Milk", "30"},               {"ABH", "Sambhar-dosa, Omeletter, Toast, Tea, Milk", "30"},                     {"HJB", "Aloo-Parantha, Dahi, Butter, Toast, Tea, Milk", "30"},             {"SNH", " Sambhar-dosa, Omelette, Toast, Tea, Milk", "30"}},
                    {{"CVR", "Rajma, Aloo-fry, Raita, Rice, Roti", "40"},                                               {"VVS", "Rajma, Aloo-fry, Raita, Rice, Roti", "40"},                                            {"ABH", "Rice, Rajma, Sabji, Dahi-bhalle, Roti, Raita", "40"},                  {"HJB", "Rice, Kale-channe, Aloo-gobhi, Roti, Raita", "40"},                {"SNH", "Rice, Rajma, Sabji, Dahi-bhalle, Roti, Raita", "40"}},
                    {{"CVR", "---CLOSED---", "-"},                                                                      {"VVS", "---CLOSED---", "-"},                                                                   {"ABH", "---CLOSED---", "-"},                                                   {"HJB", "---CLOSED---", "-"},                                               {"SNH", "---CLOSED---", "-"}},
                    {{"CVR", "---CLOSED---", "-"},                                                                      {"VVS", "---CLOSED---", "-"},                                                                   {"ABH", "---CLOSED---", "-"},                                                   {"HJB", "---CLOSED---", "-"},                                               {"SNH", "---CLOSED---", "-"}}},

            //SATURDAY
                    {{{"CVR", "Poori-sabji, Omelette, Toast, Bread-jam, Boiled-egg, Tea, Milk", "30"},                  {"VVS", "Poori-sabji, Omelette, Toast, Bread-jam, Boiled-egg, Tea, Milk", "30"},                {"ABH", "Parantha, Dahi, Toast, Omelette, Tea, Milk", "30"},                    {"HJB", "Aloo-poori, Toast, Tea, Milk", "30"},                              {"SNH", "Parantha, Dahi, Butter, Omelette, Toast, Tea, Milk", "30"}},
                    {{"CVR", "Mix-veg, Kali-masoor, Raita, Rice, Roti, Dahi, Papad, Khichdi", "40"},                    {"VVS", "Mix-veg, Kali-masoor, Raita, Rice, Roti, Dahi, Papad, Khichdi", "40"},                 {"ABH", "Khichdi, Dahi, Papad, Raita", "40"},                                   {"HJB", "Khichdi, Dahi, Papad, Raita", "40"},                               {"SNH", "Khichdi, Dahi, Papad, Raita", "40"}},
                    {{"CVR", "Bread-sandwich, Tea", "15"},                                                              {"VVS", "Bread-sandwich, Tea", "15"},                                                           {"ABH", "Aloo-sandwich, Tea", "15"},                                            {"HJB", "Aloo-sandwich, Tea", "15"},                                        {"SNH", "Aloo-sandwich, Tea", "15"}},
                    {{"CVR", "Aloo-matar-gajar, Arhar, Rice, Roti", "40"},                                              {"VVS", "Aloo-matar-gajar, Arhar, Rice, Roti", "40"},                                           {"ABH", "Soyabean-aloo, Roti, Halwa, Rice, Dal", "40"},                         {"HJB", "Aloo-sabji, Kheer, Roti, Rice, Arhar", "40"},                      {"SNH", "Soyabean, Aloo-sabji, Roti, Halwa, Rice, Dal", "40"}}},

            //SUNDAY
                    {{{"CVR", "Parantha, Dahi, Omelette, Toast, Bread-jam, Boiled-egg, Tea, Milk", "30"},               {"VVS", "Parantha, Dahi, Omelette, Toast, Bread-jam, Boiled-egg, Tea, Milk", "30"},             {"ABH", "Aloo-poori, Omelette, Toast, Tea, Milk", "30"},                        {"HJB", "Stuffed-parantha, Butter, Dahi, Toast, Tea, Milk", "30"},          {"SNH", "Aloo-poori, Omelette, Toast, Tea, Milk", "30"}},
                    {{"CVR", "Pulao-rice, Shahi-paneer, Mix-raita, Roti", "40"},                                        {"VVS", "Pulao-rice, Shahi-paneer, Mix-raita, Roti", "40"},                                     {"ABH", "Pulao-rice, Shahi-paneer, Palak, Dal, Roti, Raita", "40/50"},          {"HJB", "Palak/Shahi-paneer, Pulao-rice, Roti, Raita", "40/50"},            {"SNH", "Palak/Shahi-paneer, Pulao-rice,Dal, Roti, Raita", "40/50"}},
                    {{"CVR", "Samosa, Tea", "15"},                                                                      {"VVS", "Samosa, Tea", "15"},                                                                   {"ABH", "Samosa, Tea", "15"},                                                   {"HJB", "Samosa, Tea", "15"},                                               {"SNH", "Samosa, Tea", "15"}},
                    {{"CVR", "Mix-veg, Dal-makhani, Kheer, Rice, Roti", "40"},                                          {"VVS", "Mix-veg, Dal-makhani, Kheer, Rice, Roti", "40"},                                       {"ABH", "Bhindi/Matar-paneer, Chole, Roti, Rice, Sevaiyan, Dal", "40/50"},       {"HJB", "Chole, Mix-veg/Aloo-matar, Roti, Rice, Sevaiyan", "40"},           {"SNH", "Bhindi/Matar-Paneer, Chole, Sevaiyan, Roti, Rice", "40/50"}}},

    };

    private String[][] breakfastArray = new String[5][3];
    private String[][] lunchArray = new String[5][3];
    private String[][] teaArray = new String[5][3];
    private String[][] dinnerArray = new String[5][3];

    private RecyclerView breakfastRecyclerView;
    private RecyclerView lunchRecyclerView;
    private RecyclerView teaRecyclerView;
    private RecyclerView dinnerRecyclerView;

    private MessMenuDialogAdapter mAdapterBrk;
    private MessMenuDialogAdapter mAdapterLnch;
    private MessMenuDialogAdapter mAdapterTea;
    private MessMenuDialogAdapter mAdapterDin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_mess,container,false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        int day = bundle.getInt("position");

        TextView dayOfWeek = (TextView) view.findViewById(R.id.day_of_week);
        String[] temp = getResources().getStringArray(R.array.days_of_week);
        dayOfWeek.setText(temp[day]);

        breakfastRecyclerView = (RecyclerView) view.findViewById(R.id.breakfast_recycler_view);
        lunchRecyclerView = (RecyclerView) view.findViewById(R.id.lunch_recycler_view);
        teaRecyclerView = (RecyclerView) view.findViewById(R.id.tea_recycler_view);
        dinnerRecyclerView = (RecyclerView) view.findViewById(R.id.dinner_recycler_view);

        LinearLayoutManager mLayoutManagerBrk = new LinearLayoutManager(getActivity());
        LinearLayoutManager mLayoutManagerLnch = new LinearLayoutManager(getActivity());
        LinearLayoutManager mLayoutManagerTea = new LinearLayoutManager(getActivity());
        LinearLayoutManager mLayoutManagerDin = new LinearLayoutManager(getActivity());

        breakfastRecyclerView.setLayoutManager(mLayoutManagerBrk);
        lunchRecyclerView.setLayoutManager(mLayoutManagerLnch);
        teaRecyclerView.setLayoutManager(mLayoutManagerTea);
        dinnerRecyclerView.setLayoutManager(mLayoutManagerDin);

        breakfastArray = Finarray[day][0];
        lunchArray = Finarray[day][1];
        teaArray = Finarray[day][2];
        dinnerArray = Finarray[day][3];

        mAdapterBrk = new MessMenuDialogAdapter(getActivity(),breakfastArray);
        mAdapterLnch = new MessMenuDialogAdapter(getActivity(),lunchArray);
        mAdapterTea = new MessMenuDialogAdapter(getActivity(),teaArray);
        mAdapterDin = new MessMenuDialogAdapter(getActivity(),dinnerArray);

        breakfastRecyclerView.setAdapter(mAdapterBrk);
        lunchRecyclerView.setAdapter(mAdapterLnch);
        teaRecyclerView.setAdapter(mAdapterTea);
        dinnerRecyclerView.setAdapter(mAdapterDin);
    }

}
