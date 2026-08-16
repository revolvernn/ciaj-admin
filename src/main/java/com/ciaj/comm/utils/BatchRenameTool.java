package com.ciaj.comm.utils;

import com.ciaj.comm.exception.BsRException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @Author: Ciaj.
 * @Date: 2026/6/4 11:13
 * @Description: 批量文件重命名工具 支持多种重命名模式：替换、前缀、后缀、正则表达式、序号等
 */
public class BatchRenameTool {

    /**
     * 重命名模式枚举
     */
    public enum RenameMode {
        REPLACE,        // 字符串替换
        PREFIX,         // 添加前缀
        SUFFIX,         // 添加后缀
        REGEX,          // 正则表达式替换
        SEQUENCE,       // 序号重命名
        LOWERCASE,      // 转小写
        UPPERCASE,      // 转大写
        REMOVE_SPACES,  // 移除空格
        DATE_PREFIX,     // 日期前缀
        TIMESTAMP     // 时间戳
    }

    /**
     * 重命名配置类
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RenameConfig implements Serializable {
        private static final long serialVersionUID = 1;
        String directory;
        RenameMode mode;
        String oldText;
        String newText;
        String prefix;
        String suffix;
        String pattern;
        String replacement = "";
        Integer startNumber = 1;
        Integer step = 0;
        Boolean preview;
        Boolean recursive;

        public String getReplacement() {
            return replacement == null ? "" : replacement;
        }

        @Override
        public String toString() {
            return String.format("目录: %s, 模式: %s, 预览: %s, 递归: %s",
                    directory, mode, preview, recursive);
        }
    }

    /**
     * 文件信息类
     */
    @Data
    public static class FileInfo  implements Serializable {
        private static final long serialVersionUID = 1;
        File file;
        String oldName;
        String newName;
        Boolean willRename;

        FileInfo(File file, String oldName, String newName, boolean willRename) {
            this.file = file;
            this.oldName = oldName;
            this.newName = newName;
            this.willRename = willRename;
        }
    }

    /**
     * 主方法 - 命令行入口
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            showUsage();
            interactiveMode();
        } else {
            commandLineMode(args);
        }
    }

    /**
     * 显示使用说明
     */
    private static void showUsage() {
        System.out.println("批量文件重命名工具 v1.0");
        System.out.println("使用方法:");
        System.out.println("  1. 命令行模式: java -jar batch-rename.jar [参数]");
        System.out.println("  2. 交互模式: java -jar batch-rename.jar");
        System.out.println();
        System.out.println("命令行参数:");
        System.out.println("  -d, --directory <路径>     目标目录 (必需)");
        System.out.println("  -m, --mode <模式>         重命名模式:");
        System.out.println("        0.replace     - 字符串替换");
        System.out.println("        1.prefix      - 添加前缀");
        System.out.println("        2.suffix      - 添加后缀");
        System.out.println("        3.regex       - 正则表达式替换");
        System.out.println("        4.sequence    - 序号重命名");
        System.out.println("        5.lowercase   - 转小写");
        System.out.println("        6.uppercase   - 转大写");
        System.out.println("        8.remove-spaces - 移除空格");
        System.out.println("        8.date-prefix - 日期前缀");
        System.out.println("        9.timestamp - 时间戳");
        System.out.println("  -o, --old <文本>          要替换的旧文本 (replace模式)");
        System.out.println("  -n, --new <文本>          替换的新文本 (replace模式)");
        System.out.println("  -p, --prefix <前缀>       前缀文本 (prefix模式)");
        System.out.println("  -s, --suffix <后缀>       后缀文本 (suffix模式)");
        System.out.println("  --pattern <正则>          正则表达式 (regex模式)");
        System.out.println("  --replacement <替换文本>  替换文本 (regex模式)");
        System.out.println("  --start <数字>            起始序号 (sequence模式)");
        System.out.println("  --step <数字>             步长 (sequence模式)");
        System.out.println("  -r, --recursive           递归处理子目录");
        System.out.println("  --preview                  预览模式 (不实际重命名)");
        System.out.println("  -h, --help                 显示帮助信息");
        System.out.println();
        System.out.println("示例:");
        System.out.println("  java -jar batch-rename.jar -d /path/to/files -m replace -o \"old\" -n \"new\"");
        System.out.println("  java -jar batch-rename.jar -d /path/to/files -m prefix -p \"IMG_\"");
        System.out.println("  java -jar batch-rename.jar -d /path/to/files -m sequence --start 1 --step 1");
    }

    /**
     * 交互模式
     */
    private static void interactiveMode() {
        Scanner scanner = new Scanner(System.in);
        RenameConfig config = new RenameConfig();

        System.out.println("\n=== 交互模式 ===");

        // 获取目录
        while (true) {
            System.out.print("请输入目标目录路径: ");
            config.directory = scanner.nextLine().trim();
            if (isValidDirectory(config.directory)) {
                break;
            } else {
                System.out.println("目录不存在或不可访问，请重新输入。");
            }
        }

        // 获取重命名模式
        System.out.println("\n可用的重命名模式:");
        RenameMode[] modes = RenameMode.values();
        for (int i = 0; i < modes.length; i++) {
            System.out.println("  "+ i +"." + modes[i].name().toLowerCase());
        }
        while (true) {
            System.out.print("请输入重命名模式或模式序号: ");
            String modeInput = scanner.nextLine().trim().toUpperCase().replace("-", "_");

            try {
                int i = Integer.parseInt(modeInput);
                config.mode = RenameMode.values()[i];
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("无效的模式，请重新输入。");
            }
            try {
                config.mode = RenameMode.valueOf(modeInput);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("无效的模式，请重新输入。");
            }
        }

        // 根据模式获取其他参数
        switch (config.mode) {
            case REPLACE:
                System.out.print("请输入要替换的文本: ");
                config.oldText = scanner.nextLine().trim();
                System.out.print("请输入替换后的文本: ");
                config.newText = scanner.nextLine().trim();
                break;
            case PREFIX:
                System.out.print("请输入前缀: ");
                config.prefix = scanner.nextLine().trim();
                break;
            case SUFFIX:
                System.out.print("请输入后缀: ");
                config.suffix = scanner.nextLine().trim();
                break;
            case REGEX:
                System.out.print("请输入正则表达式: ");
                config.pattern = scanner.nextLine().trim();
                System.out.print("请输入替换文本: ");
                config.replacement = scanner.nextLine().trim();
                break;
            case SEQUENCE:
                System.out.print("请输入起始序号 (默认1): ");
                String startInput = scanner.nextLine().trim();
                config.startNumber = startInput.isEmpty() ? 1 : Integer.parseInt(startInput);
                System.out.print("请输入步长 (默认1): ");
                String stepInput = scanner.nextLine().trim();
                config.step = stepInput.isEmpty() ? 1 : Integer.parseInt(stepInput);
                break;
            case TIMESTAMP:
                System.out.print("时间戳命名模式: ");
                break;
            default:
                break;
        }

        // 其他选项
        System.out.print("是否递归处理子目录? (y/N): ");
        config.recursive = scanner.nextLine().trim().equalsIgnoreCase("y");

        System.out.print("是否预览模式? (y/N): ");
        config.preview = scanner.nextLine().trim().equalsIgnoreCase("y");

        // 执行重命名
        executeRename(config);
        scanner.close();
    }

    /**
     * 命令行模式
     */
    private static void commandLineMode(String[] args) {
        RenameConfig config = new RenameConfig();

        try {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-d":
                    case "--directory":
                        config.directory = args[++i];
                        break;
                    case "-m":
                    case "--mode":
                        String modeStr = args[++i].toUpperCase().replace("-", "_");
                        config.mode = RenameMode.valueOf(modeStr);
                        break;
                    case "-o":
                    case "--old":
                        config.oldText = args[++i];
                        break;
                    case "-n":
                    case "--new":
                        config.newText = args[++i];
                        break;
                    case "-p":
                    case "--prefix":
                        config.prefix = args[++i];
                        break;
                    case "-s":
                    case "--suffix":
                        config.suffix = args[++i];
                        break;
                    case "--pattern":
                        config.pattern = args[++i];
                        break;
                    case "--replacement":
                        config.replacement = args[++i];
                        break;
                    case "--start":
                        config.startNumber = Integer.parseInt(args[++i]);
                        break;
                    case "--step":
                        config.step = Integer.parseInt(args[++i]);
                        break;
                    case "-r":
                    case "--recursive":
                        config.recursive = true;
                        break;
                    case "--preview":
                        config.preview = true;
                        break;
                    case "-h":
                    case "--help":
                        showUsage();
                        return;
                    default:
                        System.out.println("未知参数: " + args[i]);
                        showUsage();
                        return;
                }
            }

            // 验证必需参数
            if (config.getDirectory() == null || !isValidDirectory(config.getDirectory())) {
                System.out.println("错误: 必须提供有效的目录路径");
                showUsage();
                return;
            }

            if (config.getMode() == null) {
                System.out.println("错误: 必须指定重命名模式");
                showUsage();
                return;
            }

            executeRename(config);

        } catch (Exception e) {
            System.out.println("参数解析错误: " + e.getMessage());
            showUsage();
        }
    }

    /**
     * 验证目录有效性
     */
    private static boolean isValidDirectory(String path) {
        if (path == null || path.trim().isEmpty()) {
            return false;
        }
        File dir = new File(path);
        return dir.exists() && dir.isDirectory() && dir.canRead();
    }

    /**
     * 执行重命名操作
     */
    public static List<FileInfo> executeRename(RenameConfig config) {
        System.out.println("\n开始处理重命名...");
        System.out.println("配置: " + config);

        List<FileInfo> fileList = collectFiles(config);

        if (fileList.isEmpty()) {
            System.out.println("未找到符合条件的文件。");
            return fileList;
        }

        // 生成新文件名
        generateNewNames(fileList, config);

        // 显示预览
        showPreview(fileList);

        // 如果是预览模式，直接返回
        if (config.getPreview()) {
            System.out.println("\n预览模式结束，未执行实际重命名。");
            return fileList;
        }

        // 确认操作
        if (!confirmOperation()) {
            System.out.println("操作已取消。");
            return fileList;
        }

        // 执行实际重命名
        performRename(fileList);

        return fileList;
    }

    /**
     * 收集文件列表
     */
    public static List<FileInfo> collectFiles(RenameConfig config) {
        if (config.getDirectory() == null || !isValidDirectory(config.getDirectory())) {
            throw new BsRException(String.format( "%s 不是有效的目录,请填写有效的目录", config.getDirectory()));
        }
        List<FileInfo> fileList = new ArrayList<>();
        File directory = new File(config.getDirectory());

        collectFilesRecursive(directory, fileList, config.getRecursive());

        return fileList;
    }

    /**
     * 递归收集文件
     */
    private static void collectFilesRecursive(File dir, List<FileInfo> fileList, boolean recursive) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isFile()) {
                fileList.add(new FileInfo(file, file.getName(), "", true));
            } else if (file.isDirectory() && recursive) {
                collectFilesRecursive(file, fileList, recursive);
            }
        }
    }

    /**
     * 生成新文件名
     */
    private static void generateNewNames(List<FileInfo> fileList, RenameConfig config) {
        int sequence = config.getStartNumber();
         int index = 0;
        for (FileInfo fileInfo : fileList) {
            String newName = fileInfo.getOldName()  ;
            int dotIndex = newName.lastIndexOf('.');
            String extension = "";
            String name = "";
            if (dotIndex > 0) {
                name = newName.substring(0, dotIndex);
                extension = newName.substring(dotIndex);
            }
            switch (config.getMode()) {
                case REPLACE:
                    newName = String.format("%s%s",name.replace(config.getOldText(), config.getNewText()), extension);
                    break;
                case PREFIX:
                    newName = String.format("%s%s%s",config.getPrefix(), name, extension);
                    break;
                case SUFFIX:
                    newName = String.format("%s%s%s", name, config.getSuffix(), extension);
                    break;
                case REGEX:
                    newName =String.format("%s%s",  name.replaceAll(config.getPattern(), config.getReplacement()), extension);;
                    break;
                case SEQUENCE:
                    newName = String.format("%04d_%s%s", sequence, name, extension);
                    sequence += config.getStep();
                    break;
                case LOWERCASE:
                    newName = String.format("%s%s",name.toLowerCase(), extension);
                    break;
                case UPPERCASE:
                    newName = String.format("%s%s",name.toUpperCase(), extension);
                    break;
                case REMOVE_SPACES:
                    newName = String.format("%s%s",name.replaceAll("\\s+", "_"), extension);
                    break;
                case DATE_PREFIX:
                    String dateStr = new SimpleDateFormat("yyyyMMdd_").format(new Date());
                    newName = String.format("%s%s%s", dateStr, name, extension);
                    break;
                case TIMESTAMP:
                    newName = String.format("%s%s%s",System.currentTimeMillis(),index, extension);
                    break;
            }

            fileInfo.setNewName(newName);
            fileInfo.setWillRename( !fileInfo.getOldName().equals(fileInfo.getNewName()));
            index += 1;
        }
    }

    /**
     * 显示预览
     */
    private static void showPreview(List<FileInfo> fileList) {
        System.out.println("\n=== 重命名预览 ===");
        int renameCount = 0;

        for (FileInfo fileInfo : fileList) {
            if (fileInfo.getWillRename()) {
                System.out.printf("✓ %s\n  → %s\n", fileInfo.getOldName(), fileInfo.getNewName());
                renameCount++;
            } else {
                System.out.printf("  %s (无变化)\n", fileInfo.getOldName());
            }
        }

        System.out.printf("\n总计: %d 个文件，其中 %d 个文件将被重命名\n",
                fileList.size(), renameCount);
    }

    /**
     * 确认操作
     */
    private static boolean confirmOperation() {
        if (System.console() == null) {
            // 非交互式环境，自动确认
            return true;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("\n确认执行重命名操作? (y/N): ");
        String input = scanner.nextLine().trim();
        return input.equalsIgnoreCase("y");
    }

    /**
     * 执行实际重命名
     */
    private static void performRename(List<FileInfo> fileList) {
        int successCount = 0;
        int failCount = 0;

        System.out.println("\n开始执行重命名...");

        for (FileInfo fileInfo : fileList) {
            if (!fileInfo.getWillRename()) {
                continue;
            }

            try {
                Path source = fileInfo.file.toPath();
                Path target = source.resolveSibling(fileInfo.getNewName());

                Files.move(source, target);
                System.out.printf("✓ 成功: %s → %s\n", fileInfo.getOldName(), fileInfo.getNewName());
                successCount++;

            } catch (Exception e) {
                System.out.printf("✗ 失败: %s → %s (%s)\n",
                        fileInfo.getOldName(), fileInfo.getNewName(), e.getMessage());
                failCount++;
            }
        }

        System.out.printf("\n重命名完成: 成功 %d, 失败 %d\n", successCount, failCount);
    }
}