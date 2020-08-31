package com.gadgetsfolk.admin.ncertbooks.model;

public class Chapter {
    private String chapter_name;
    private String chapter_pdf_url;
    private String pdf_name;

    public Chapter() { }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getChapter_pdf_url() {
        return chapter_pdf_url;
    }

    public void setChapter_pdf_url(String chapter_pdf_url) {
        this.chapter_pdf_url = chapter_pdf_url;
    }

    public String getPdf_name() {
        return pdf_name;
    }

    public void setPdf_name(String pdf_name) {
        this.pdf_name = pdf_name;
    }

}
