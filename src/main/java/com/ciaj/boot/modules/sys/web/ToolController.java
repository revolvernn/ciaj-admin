package com.ciaj.boot.modules.sys.web;

import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.annotation.OperationLog;
import com.ciaj.comm.annotation.Resubmit;
import com.ciaj.comm.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/19 14:27
 * @Description:
 */
@Api(tags = "系统-工具")
@Controller
@RequestMapping("sys/tool")
public class ToolController {

	/**
	 * 批量变更文件名预览
	 *
	 * @param entity
	 * @return
	 */
	@ResponseBody
        @Resubmit
        @ApiOperation(value = "批量变更文件名预览", produces = "application/json;charset=UTF-8")
        @OperationLog(operation = "系统-工具", content = "批量变更文件名预览")
        @RequiresPermissions("sys:tool:batch-rename:preview")
        @PostMapping("batch-rename/preview")
        public ResponseEntity batchRenamePreview(@RequestBody BatchRenameTool.RenameConfig entity){
        List<BatchRenameTool.FileInfo> fileInfos = BatchRenameTool.executeRename(entity);
        com.ciaj.comm.utils.Page page = new Page(fileInfos, fileInfos.size(), fileInfos.size(), 1, false);
        return new ResponseEntity().put(page);
	}
	/**
	 * 批量变更文件名
	 *
	 * @param entity
	 * @return
	 */
	@ResponseBody
        @Resubmit
        @ApiOperation(value = "批量变更文件名", produces = "application/json;charset=UTF-8")
        @OperationLog(operation = "系统-工具", content = "批量变更文件名")
        @RequiresPermissions("sys:tool:batch-rename:execute")
        @PostMapping("batch-rename/execute")
        public ResponseEntity batchRenameExecute(@RequestBody BatchRenameTool.RenameConfig entity){
        List<BatchRenameTool.FileInfo> fileInfos = BatchRenameTool.executeRename(entity);
        com.ciaj.comm.utils.Page page = new Page(fileInfos, fileInfos.size(), fileInfos.size(), 1, false);
        return new ResponseEntity().put(page);
	}


}
