package com.aviator.elearning.aviator.main;

import android.content.Context;
import android.util.Log;

import com.aviator.elearning.el.models.CourseExamModel;
import com.aviator.elearning.el.models.CourseModel;
import com.aviator.elearning.el.models.ExamModel;
import com.aviator.elearning.el.models.ExamOptionsModel;
import com.aviator.elearning.el.models.ExamQAModel;
import com.aviator.elearning.el.models.ExamQnModel;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.models.SubtopicsModel;
import com.aviator.elearning.el.models.TopicSubtopicModel;
import com.aviator.elearning.el.models.TopicsModel;
import com.aviator.elearning.el.models.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class ParserJsons {

    private Context context;

    public ParserJsons(Context context) {
        this.context = context;
    }

    public UserModel getUserModel(String jsObject) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsObject);
            if (jsonObject.has("user_name")) {

                UserModel userModel = new UserModel();
                userModel.setUser_id(jsonObject.getInt("user_id"));
                userModel.setUser_name(jsonObject.getString("user_name"));
                userModel.setUser_email(jsonObject.getString("user_email"));
                userModel.setUser_contact(jsonObject.getString("user_contact"));
                userModel.setUser_password(jsonObject.getString("user_password"));
                userModel.setUser_passwordsalt(jsonObject.getString("user_passwordsalt"));
                userModel.setUser_image(jsonObject.getString("user_image"));
                userModel.setUser_status(jsonObject.getString("user_status"));
                userModel.setUser_type(jsonObject.getString("user_type"));
                userModel.setCountry(jsonObject.getString("user_country"));
                return userModel;
            }
        } catch (JSONException ignored) {
            Log.e("getUserModel: ", ignored.toString());
        }

        return null;
    }

    private UserModel getUserModel(JSONObject jsonObject) {
        try {
            if (jsonObject.has("user_name")) {

                UserModel userModel = new UserModel();
                userModel.setUser_id(jsonObject.getInt("user_id"));
                userModel.setUser_name(jsonObject.getString("user_name"));
                userModel.setUser_email(jsonObject.getString("user_email"));
                userModel.setUser_contact(jsonObject.getString("user_contact"));
                userModel.setUser_password(jsonObject.getString("user_password"));
                userModel.setUser_passwordsalt(jsonObject.getString("user_passwordsalt"));
                userModel.setUser_image(jsonObject.getString("user_image"));
                userModel.setUser_status(jsonObject.getString("user_status"));
                userModel.setUser_type(jsonObject.getString("user_type"));
                userModel.setCountry(jsonObject.getString("user_country"));
                return userModel;
            }
        } catch (JSONException ignored) {
            Log.e("getUserModel: ", ignored.toString());
        }

        return null;
    }

    public ArrayList<UserModel> userModelArrayList(String data) {

        try {
            JSONArray jsonArray = new JSONArray(data);
            ArrayList<UserModel> models = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                UserModel examModel = getUserModel(jsonObject);
                if (examModel != null) {
                    models.add(examModel);
                } else {
                    return null;
                }
            }

            return models;

        } catch (JSONException ignored) {
            Log.e("userModelArrayList:---", ignored.toString());
        }

        return null;

    }

    public MessageModel getMessageModel(String jsObject) {
        try {
            JSONObject jsonObject = new JSONObject(jsObject);
            MessageModel messageModel = new MessageModel();
            messageModel.setError(jsonObject.getBoolean("error"));
            messageModel.setMessage(jsonObject.getString("message"));
            return messageModel;
        } catch (JSONException ignored) {
            Log.e("getMessageModel: ", ignored.toString());
        }
        return null;
    }

    public ArrayList<ExamModel> getExamData(String data) {

        try {
            JSONArray jsonArray = new JSONArray(data);
            ArrayList<ExamModel> models = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ExamModel examModel = getExamModel(jsonObject);
                if (examModel != null) {
                    models.add(examModel);
                } else {
                    return null;
                }
            }

            return models;

        } catch (JSONException ignored) {
            Log.e("getExamData:---", ignored.toString());
        }

        return null;

    }

    private ExamModel getExamModel(JSONObject jsonObject) {
        try {
            ExamModel examModel = new ExamModel();
            examModel.setE_id(jsonObject.getInt("e_id"));
            examModel.setE_duration(jsonObject.getDouble("e_duration"));
            examModel.setE_courseuniqid(jsonObject.getString("e_courseuniqid"));
            examModel.setE_useremail(jsonObject.getString("e_useremail"));
            examModel.setE_status(jsonObject.getString("e_status"));
            examModel.setE_coverimg(jsonObject.getString("e_coverimg"));
            examModel.setE_examuniqid(jsonObject.getString("e_examuniqid"));
            examModel.setE_approved(jsonObject.getString("e_approved"));
            examModel.setE_date(jsonObject.getString("e_date"));
            return examModel;
        } catch (JSONException ignored) {
            Log.e("getExamModel:---", ignored.toString());
        }
        return null;
    }

    public ArrayList<CourseModel> getCourseData(String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            ArrayList<CourseModel> models = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                CourseModel courseModel = getCourseModel(jsonArray.getJSONObject(i));
                if (courseModel != null) {
                    models.add(courseModel);
                } else {
                    return null;
                }
            }

            return models;

        } catch (JSONException ignored) {
            Log.e("getCourseData:---", ignored.toString());
        }

        return null;

    }

    private CourseModel getCourseModel(JSONObject jsonObject) {
        CourseModel courseModel = new CourseModel();
        try {
            courseModel.setC_id(jsonObject.getInt("c_id"));
            courseModel.setC_name(jsonObject.getString("c_name"));
            courseModel.setC_uniqid(jsonObject.getString("c_uniqid"));
            courseModel.setC_useremail(jsonObject.getString("c_useremail"));
            courseModel.setC_approved(jsonObject.getString("c_approved"));
            courseModel.setC_coverimg(jsonObject.getString("c_coverimg"));
            courseModel.setC_date(jsonObject.getString("c_date"));

            return courseModel;
        } catch (JSONException ignored) {
            Log.e("getCourseModel:---", ignored.toString());
        }
        return null;
    }

    public ArrayList<TopicsModel> getTopics(String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);

            ArrayList<TopicsModel> topicsModelArrayList = new ArrayList<>();
            int i = 0;
            while (i < jsonArray.length()) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                TopicsModel topicsModel = getTopicsModel(jsonObject);
                if (topicsModel != null) {
                    topicsModelArrayList.add(topicsModel);
                }
                i++;
            }

            return topicsModelArrayList;

        } catch (JSONException ignored) {
            Log.e("getTopics:---", ignored.toString());

        }
        return null;
    }

    public ArrayList<CourseExamModel> getCourseExamModel(String data) {
//        Log.e("yonthaniel: ", data);
        ArrayList<CourseExamModel> modelArrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Log.e("Yonathaniel", jsonObject.getString("e_useremail"));
//                Log.e("Yonathaniel", jsonObject.getString("c_name"));
//            }

            return getCourseExamModel_(jsonArray, modelArrayList);
        } catch (JSONException ignored) {
            Log.e("getCourseExamModel:---", ignored.toString());
        }
        return null;
    }

    private ArrayList<CourseExamModel> getCourseExamModel_(JSONArray data, ArrayList<CourseExamModel> result) {
        for (int i = 0; i < data.length(); i++) {
            try {
                JSONObject jsonObject = data.getJSONObject(i);
                CourseExamModel courseExamModel = new CourseExamModel();

                CourseModel courseModel = getCourseModel(jsonObject);
                ExamModel examModel = getExamModel(jsonObject);
                courseExamModel.setCourseModel(courseModel);
                courseExamModel.setExamModel(examModel);
                if (courseModel != null && examModel != null) {
                    result.add(courseExamModel);
                }
            } catch (JSONException e) {
                Log.e("getCourseExamModel_ :", e.toString());
                return null;
            }
        }
        return result;
    }

    private ExamQnModel getExamQnModel(JSONObject jsonObject) {
        try {
            ExamQnModel examQnModel = new ExamQnModel();
//            ArrayList<ExamQnModel> arrayList = new ArrayList<>();
            examQnModel.setQn_id(jsonObject.getInt("qn_id"));
            examQnModel.setQn_examuniqid(jsonObject.getString("qn_examuniqid"));
            examQnModel.setQn_examqn(jsonObject.getString("qn_examqn"));
//            arrayList.add(examQnModel);
            return examQnModel;
        } catch (JSONException e) {
            Log.e("getExamQnModel :", e.toString());
        }
        return null;
    }

    public ArrayList<ExamQnModel> getExamQnModel(String data) {
        ArrayList<ExamQnModel> modelArrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            return getExamQnModel_(jsonArray, modelArrayList);
        } catch (JSONException ignored) {
            Log.e("getCourseExamModel:---", ignored.toString());
        }
        return null;
    }

    private ArrayList<ExamQnModel> getExamQnModel_(JSONArray jsonArray, ArrayList<ExamQnModel> result) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                ExamQnModel examQnModel = getExamQnModel(jsonObject);
                result.add(getExamQnModel(jsonObject));
            } catch (JSONException e) {
                Log.e("getExamQnModel_ :", e.toString());
                return null;
            }
        }
        return result;
    }

    private ExamOptionsModel getExamOptionsAnswer(JSONObject jsonObject) {
        try {
            ExamOptionsModel examOptionsModel = new ExamOptionsModel();
//            ArrayList<ExamOptionsModel> arrayList = new ArrayList<>();
            examOptionsModel.setOpt_id(jsonObject.getInt("opt_id"));
            examOptionsModel.setOpt_qnid(jsonObject.getInt("opt_qnid"));
            examOptionsModel.setOpt_option1(jsonObject.getString("opt_option1"));
            examOptionsModel.setOpt_option2(jsonObject.getString("opt_option2"));
            examOptionsModel.setOpt_option3(jsonObject.getString("opt_option3"));
            examOptionsModel.setOpt_option4(jsonObject.getString("opt_option4"));
            examOptionsModel.setOpt_option5(jsonObject.getString("opt_option5"));
            examOptionsModel.setOpt_option6(jsonObject.getString("opt_option6"));
            examOptionsModel.setOpt_optionAnswer(jsonObject.getString("opt_optionAnswer"));
            examOptionsModel.setOpt_examuniqid(jsonObject.getString("opt_examuniqid"));
//            arrayList.add(examOptionsModel);
            return examOptionsModel;
        } catch (JSONException e) {
            Log.e("getExamOptionsAnswer :", e.toString());
        }
        return null;
    }

    public ArrayList<ExamQAModel> getExamQA(String data) {
        try {
            ArrayList<ExamQAModel> examQAModels = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(data);
            return getExamQA_(jsonArray, examQAModels);
        } catch (JSONException e) {
            Log.e("getExamQA :", e.toString());
        }
        return null;
    }

    private ArrayList<ExamQAModel> getExamQA_(JSONArray jsonArray, ArrayList<ExamQAModel> result) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ExamQAModel examQAModel = new ExamQAModel();
                ExamQnModel examQnModel = getExamQnModel(jsonObject);
                ExamOptionsModel examOptionsModel = getExamOptionsAnswer(jsonObject);
                examQAModel.setExamQnModel(examQnModel);
                examQAModel.setExamOptionsModel(examOptionsModel);

                result.add(examQAModel);
            } catch (JSONException e) {
                Log.e("getExamQA_ :", e.toString());
                return null;
            }
        }
        return result;
    }

    private TopicsModel getTopicsModel(JSONObject jsonObject) {
        try {
            TopicsModel topicsModel = new TopicsModel();
            topicsModel.setTopicid(jsonObject.getInt("ct_id"));
            topicsModel.setCourseUniqId(jsonObject.getString("ct_courseuniqid"));
            topicsModel.setTopic(jsonObject.getString("ct_topic"));
            return topicsModel;
        } catch (JSONException e) {
            Log.e("getTopicsModel :", e.toString());
            return null;
        }
    }

    private SubtopicsModel getSubtopicsModel(JSONObject jsonObject) {
        try {
            SubtopicsModel topicsModel = new SubtopicsModel();
            topicsModel.setCst_id(jsonObject.getInt("cst_id"));
            topicsModel.setCst_topicid(jsonObject.getInt("cst_topicid"));
            topicsModel.setCst_subtopic(jsonObject.getString("cst_subtopic"));
            topicsModel.setCst_content(jsonObject.getString("cst_content"));
            return topicsModel;
        } catch (JSONException e) {
            Log.e("getTopicsModel :", e.toString());
            return null;
        }
    }

    private TopicSubtopicModel getTopicSubtopicModel(JSONObject jsonObject) {
        TopicSubtopicModel topicSubtopicModel = new TopicSubtopicModel();
        JSONObject jsTopic = null;
        try {
            jsTopic = jsonObject.getJSONObject("topic");
            TopicsModel topicsModel = getTopicsModel(jsTopic);
            if (topicsModel == null) {
                return null;
            }
            topicSubtopicModel.setTopicsModel(topicsModel);
        } catch (JSONException e) {
            return null;
        }

        ArrayList<SubtopicsModel> arrayList = new ArrayList<>();
        try {
            JSONArray jsSubTopics = jsonObject.getJSONArray("subtopics");
            for (int i = 0; i < jsSubTopics.length(); i++) {
                SubtopicsModel subtopicsModel = getSubtopicsModel(jsSubTopics.getJSONObject(i));
                if (subtopicsModel != null) {
                    arrayList.add(subtopicsModel);
                }
            }
            topicSubtopicModel.setSubtopicsModelArrayList(arrayList);
        } catch (JSONException e) {
            Log.e("getTopicSubtopicModel :", e.toString());
        }

        return topicSubtopicModel;
    }

    public ArrayList<TopicSubtopicModel> getTopicSubtopicModels(String s) {
        try {
            JSONArray jsonArray = new JSONArray(s);

            Log.e("Yonathaniel:", String.valueOf(jsonArray.length()));

            ArrayList<TopicSubtopicModel> modelArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                TopicSubtopicModel topicSubtopicModel = getTopicSubtopicModel(jsonArray.getJSONObject(i));
                Log.e("Aviator:", String.valueOf(i));
                if (topicSubtopicModel != null) {
                    modelArrayList.add(topicSubtopicModel);
                }
            }

            Log.e("Yonah:", String.valueOf(modelArrayList.size()));
            return modelArrayList;
        } catch (JSONException e) {
            Log.e("getTopicsModel :", e.toString());
            return null;
        }
    }
}
