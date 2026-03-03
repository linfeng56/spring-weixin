package com.github.linfeng.app.dto;

import java.util.ArrayList;
import java.util.List;

public class BatchImportResult {

    private int total;
    private int success;
    private int failed;
    private List<ImportError> errors = new ArrayList<>();

    public static class ImportError {
        private int row;
        private String field;
        private String message;

        public ImportError(int row, String field, String message) {
            this.row = row;
            this.field = field;
            this.message = message;
        }

        public int getRow() { return row; }
        public void setRow(int row) { this.row = row; }

        public String getField() { return field; }
        public void setField(String field) { this.field = field; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public int getSuccess() { return success; }
    public void setSuccess(int success) { this.success = success; }

    public int getFailed() { return failed; }
    public void setFailed(int failed) { this.failed = failed; }

    public List<ImportError> getErrors() { return errors; }
    public void setErrors(List<ImportError> errors) { this.errors = errors; }
}
