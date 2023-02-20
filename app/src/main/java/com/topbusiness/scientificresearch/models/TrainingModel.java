package com.topbusiness.scientificresearch.models;

import java.io.Serializable;
import java.util.List;

public class TrainingModel implements Serializable {
    private int course_id_pk;
    private String course_name;
    private String course_date;
    private String course_image;
    private String course_capacity;
    private int course_days;
    private String course_desc;
    private String course_funds;
    private int reservation;
    private List<VideoModel> courses_video;

    public int getCourse_id_pk() {
        return course_id_pk;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getCourse_date() {
        return course_date;
    }

    public String getCourse_image() {
        return course_image;
    }

    public String getCourse_capacity() {
        return course_capacity;
    }

    public int getCourse_days() {
        return course_days;
    }

    public String getCourse_desc() {
        return course_desc;
    }

    public String getCourse_funds() {
        return course_funds;
    }

    public List<VideoModel> getCourses_video() {
        return courses_video;
    }

    public int getReservation() {
        return reservation;
    }

    public class VideoModel implements Serializable
    {
        private String id_video;
        private String video_title;
        private String video;
        private String course_id_fk;
        private String video_duration;


        public String getId_video() {
            return id_video;
        }

        public String getVideo_title() {
            return video_title;
        }

        public String getVideo() {
            return video;
        }

        public String getCourse_id_fk() {
            return course_id_fk;
        }

        public String getVideo_duration() {
            return video_duration;
        }
    }
}
