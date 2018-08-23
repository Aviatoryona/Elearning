package com.aviator.elearning.aviator.main;

import com.aviator.elearning.R;
import com.aviator.elearning.el.models.List_Side_Model;

import java.util.ArrayList;
import java.util.List;

public class List_Side_Data {

    public static List<List_Side_Model> datas = new ArrayList<>();

    static {
        List_Side_Model list_side_model;

        list_side_model = new List_Side_Model();
        list_side_model.imgRes = R.drawable.ic_check_circle_black_24dp;
        list_side_model.titleDesc = "Courses";
        datas.add(list_side_model);

        list_side_model = new List_Side_Model();
        list_side_model.imgRes = R.drawable.ic_library_books_black_24dp;
        list_side_model.titleDesc = "Exams";
        datas.add(list_side_model);

        list_side_model = new List_Side_Model();
        list_side_model.imgRes = R.drawable.ic_laptop_chromebook_black_24dp;
        list_side_model.titleDesc = "Lab";
        datas.add(list_side_model);

        list_side_model = new List_Side_Model();
        list_side_model.imgRes = R.drawable.ic_person_black_24dp;
        list_side_model.titleDesc = "Account";
        datas.add(list_side_model);

        list_side_model = new List_Side_Model();
        list_side_model.imgRes = R.drawable.ic_notifications_none_black_24dp;
        list_side_model.titleDesc = "Notification";
        datas.add(list_side_model);

        list_side_model = new List_Side_Model();
        list_side_model.imgRes = R.drawable.ic_settings_black_24dp;
        list_side_model.titleDesc = "Settings";
        datas.add(list_side_model);

        list_side_model = new List_Side_Model();
        list_side_model.imgRes = R.drawable.ic_share_black_24dp;
        list_side_model.titleDesc = "Share";
        datas.add(list_side_model);

        list_side_model = new List_Side_Model();
        list_side_model.imgRes = R.drawable.ic_close_black_24dp;
        list_side_model.titleDesc = "Quit";
        datas.add(list_side_model);
    }

//tutor,admin manage,student

    public static final // {
            String
            Account = "Account",
            Notification = "Notification",
            Courses = "Courses",
            Exams = "Exams",
            Lab = "Lab",
            Settings = "Settings",
            Share = "Share",
            Quit = "Quit";
    //}
}
