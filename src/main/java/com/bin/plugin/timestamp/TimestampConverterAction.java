package com.bin.plugin.timestamp;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.StringSelection;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimestampConverterAction extends AnAction {
    private static final DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneOffset.UTC);

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String input = Messages.showInputDialog(
            "Enter timestamp (milliseconds):",
            "Timestamp Converter",
            Messages.getQuestionIcon()
        );

        if (input == null || input.trim().isEmpty()) {
            return;
        }

        try {
            long timestamp = Long.parseLong(input.trim());
            String formattedDate = formatter.format(Instant.ofEpochMilli(timestamp));
            
            // 复制到剪贴板
            CopyPasteManager.getInstance().setContents(new StringSelection(formattedDate));
            
            // 显示成功消息
            Messages.showInfoMessage(
                "Converted time: " + formattedDate + "\n(Copied to clipboard)",
                "Conversion Result"
            );
        } catch (NumberFormatException ex) {
            Messages.showErrorDialog(
                "Please enter a valid timestamp number",
                "Invalid Input"
            );
        }
    }
} 