package com.bazl.hslims.manager.model.po;

import java.io.Serializable;

public class LimsBoardSample implements Serializable {
    private Integer id;

    private Integer boardId;

    private Integer sampleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public Integer getSampleId() {
        return sampleId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }
}