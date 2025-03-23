package com.github.linfeng.plan.service;

import com.github.linfeng.plan.entity.PlanWeeks;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class WeekPlanExportService {

    public byte[] exportToWord(List<PlanWeeks> plans, String type) throws Exception {
        try (XWPFDocument document = new XWPFDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            XWPFParagraph title = document.createParagraph();
            title.setAlignment(org.apache.poi.util.EnumUtil.getAlignment(org.apache.poi.sl.draw.geom.Enum.GetFromClass.class, org.apache.poi.common.usermodel.Enum.GetFromClass.class, 1));
            XWPFRun titleRun = title.createRun();
            titleRun.setText("周计划导出");
            titleRun.setFontSize(20);
            titleRun.setBold(true);

            for (PlanWeeks plan : plans) {
                XWPFParagraph p = document.createParagraph();
                XWPFRun run = p.createRun();
                run.setText("标题: " + plan.getTitle());
                run.setFontSize(14);
                run.setBold(true);

                XWPFParagraph p2 = document.createParagraph();
                XWPFRun run2 = p2.createRun();
                run2.setText("时间: " + formatDate(plan.getBeginDate()) + " - " + formatDate(plan.getEndDate()));
                run2.setFontSize(12);

                if (plan.getRemarks() != null && !plan.getRemarks().isEmpty()) {
                    XWPFParagraph p3 = document.createParagraph();
                    XWPFRun run3 = p3.createRun();
                    run3.setText("备注: " + plan.getRemarks());
                    run3.setFontSize(12);
                }

                if (plan.getSummary() != null && !plan.getSummary().isEmpty()) {
                    XWPFParagraph p4 = document.createParagraph();
                    XWPFRun run4 = p4.createRun();
                    run4.setText("总结: " + plan.getSummary());
                    run4.setFontSize(12);
                }
            }

            document.write(out);
            return out.toByteArray();
        }
    }

    private String formatDate(Long timestamp) {
        if (timestamp == null) return "";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new java.util.Date(timestamp));
    }
}
