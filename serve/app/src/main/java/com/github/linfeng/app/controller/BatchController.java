package com.github.linfeng.app.controller;

import com.alibaba.excel.EasyExcel;
import com.github.linfeng.app.dto.ApiResponse;
import com.github.linfeng.app.dto.BatchImportResult;
import com.github.linfeng.app.dto.WeekPlanExcelDTO;
import com.github.linfeng.app.enums.PlanStatus;
import com.github.linfeng.app.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/week-plans/batch")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("周计划导入模板", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), WeekPlanExcelDTO.class)
                .sheet("周计划")
                .doWrite(new ArrayList<>());
    }

    @PostMapping("/import")
    public ApiResponse<BatchImportResult> importExcel(
            @RequestParam("file") MultipartFile file,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId) {
        try {
            BatchImportResult result = batchService.importFromExcel(file, userId);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("导入失败: " + e.getMessage());
        }
    }

    @PutMapping("/status")
    public ApiResponse<Void> batchUpdateStatus(@RequestBody BatchStatusRequest request) {
        try {
            PlanStatus status = PlanStatus.valueOf(request.getStatus());
            batchService.batchUpdateStatus(request.getIds(), status, request.getRemark());
            return ApiResponse.success(null);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @DeleteMapping
    public ApiResponse<Void> batchDelete(@RequestBody BatchDeleteRequest request) {
        try {
            batchService.batchDelete(request.getIds());
            return ApiResponse.success(null);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    public static class BatchStatusRequest {
        private List<Long> ids;
        private String status;
        private String remark;

        public List<Long> getIds() { return ids; }
        public void setIds(List<Long> ids) { this.ids = ids; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getRemark() { return remark; }
        public void setRemark(String remark) { this.remark = remark; }
    }

    public static class BatchDeleteRequest {
        private List<Long> ids;

        public List<Long> getIds() { return ids; }
        public void setIds(List<Long> ids) { this.ids = ids; }
    }
}
