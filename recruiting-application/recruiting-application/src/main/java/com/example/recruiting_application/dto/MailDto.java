package com.example.recruiting_application.dto;

public class MailDto {

    private String to;
    private String from;
    private String subject;
    private String text;

    // No-argument constructor
    public MailDto() {
    }

    // All-argument constructor
    public MailDto(String to, String from, String subject, String text) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.text = text;
    }

    // Getters and setters
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

//    public static class MailBody {
//
//        private String to;
//        private String from;
//        private String subject;
//        private String text;
//
//        // No-argument constructor
//        public MailBody() {
//        }
//
//        // All-argument constructor
//        public MailBody(String to, String from, String subject, String text) {
//            this.to = to;
//            this.from = from;
//            this.subject = subject;
//            this.text = text;
//        }
//
//        // Getters and setters
//        public String getTo() {
//            return to;
//        }
//
//        public void setTo(String to) {
//            this.to = to;
//        }
//
//        public String getFrom() {
//            return from;
//        }
//
//        public void setFrom(String from) {
//            this.from = from;
//        }
//
//        public String getSubject() {
//            return subject;
//        }
//
//        public void setSubject(String subject) {
//            this.subject = subject;
//        }
//
//        public String getText() {
//            return text;
//        }
//
//        public void setText(String text) {
//            this.text = text;
//        }
//    }
//}
