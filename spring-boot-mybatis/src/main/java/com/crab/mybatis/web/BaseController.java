package com.crab.mybatis.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.crab.mybatis.domain.RequestObj;
import com.crab.mybatis.exception.SystemException;
import com.crab.mybatis.largeupload.FileUnifier;
import com.crab.mybatis.service.SysDataDicService;
import com.crab.mybatis.utils.FileUtil;

/**
 * Base controller.
 * 
 * @author YunxiaoXie
 *
 */
@Controller
public class BaseController {
	private static final Logger Logger = LoggerFactory.getLogger(BaseController.class);
	/**
	 * 断点续传分隔符
	 */
	private static final String BREAKPOINT = "---";
	/**
	 * 文件目录
	 */
	private static final String FILE_DIR = "d:/images/";

	@Autowired
	private SysDataDicService service;
	
	/**
	 * 返回前台通用接口规范
	 * <功能详细描述>
	 * @author LimingWang
	 * @param code
	 * @param msg
	 * @param content
	 * @return
	 * @date 2017年3月14日 下午7:56:03
	 */
	public ModelMap retResult(String code, String msg, Object content){
		ModelMap modelMap = new ModelMap();
		modelMap.put("code", code);
		modelMap.put("msg", msg);
		modelMap.put("result", content);
		return modelMap;
	}

	/**
	 * 查询用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("dataDic/{code}")
	public String findUser(@PathVariable int code) {
		// if (1004 == code) {
		// return "[{\"id\":1, \"name\":\"看书\", \"model\":\"book\"},{\"id\":2,
		// \"name\":\"跑步\", \"model\":\"run\"},{\"id\":3, \"name\":\"打球\",
		// \"model\":\"ball\"}]";
		// }
		if (1005 == code) {
			return "[{\"id\":1, \"comet\":\"语文\", \"dic_value\":\"yw\"},{\"id\":2, \"comet\":\"数学\", \"dic_value\":\"sx\"},{\"id\":3, \"comet\":\"英语\", \"dic_value\":\"english\"}]";
		}
		if (1006 == code) {
			return "[{\"id\": 1,\"pId\": 0,\"name\": \"普通的父节点\",\"title\": \"我很普通，随便点我吧\",\"open\": true},"
					+ "{\"id\": 11,\"pId\": 1,\"name\": \"叶子节点 - 1\",\"title\": \"我很普通，随便点我吧\",\"ui-sref\":\"user\"},"
					+ "{\"id\": 2,\"pId\": 0,\"name\": \"NB的父节点\",\"title\": \"点我可以，但是不能点我的子节点，有本事点一个你试试看？\",\"open\": true},"
					+ "{\"id\": 21,\"pId\": 2,\"name\": \"叶子节点2 - 1\",\"title\": \"你哪个单位的？敢随便点我？小心点儿..\",\"click\": false,\"ui-sref\":\"user\"},"
					+ "{\"id\": 3,\"pId\": 0,\"name\": \"郁闷的父节点\",\"title\": \"别点我，我好害怕...我的子节点随便点吧...\",\"open\": true,\"click\": false},"
					+ "{\"id\": 31,\"pId\": 3,\"name\": \"叶子节点3 - 1\",\"title\": \"唉，随便点我吧\",\"ui-sref\":\"user\"}]";
		}
		return JSON.toJSONString(service.findByCode(code));
	}

	@ResponseBody
	@RequestMapping(value = "/upload2", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	public String upload2(@RequestParam(value = "file", required = false) MultipartFile[] files,
			HttpServletRequest request) {
		for (MultipartFile file : files) {
			if (file.isEmpty())
				continue;
			System.out.println(file.getContentType() + "," + file.getName() + "," + file.getOriginalFilename());
		}
		return "-1";
	}

	@ResponseBody
	@RequestMapping("/upload")
	public String upload(@RequestParam("name") String destination, HttpServletRequest request) {

		try {
			// Servlet3.0方式上传文件
			Collection<Part> parts = request.getParts();

			for (Part part : parts) {
				if (part.getContentType() != null) { // 忽略路径字段,只处理文件类型
					String fileName = FileUtil.getFileName(part.getHeader("content-disposition"));
					File f = new File(FILE_DIR + fileName);
					FileUtil.write(part.getInputStream(), f);
				}
			}
		} catch (Exception e) {
			Logger.error("upload large file error:",	e);
		}
		return "-1";
	}

	@ResponseBody
	@RequestMapping("/uploadLarge")
	public String uploadLarge(@RequestParam("name") String destination, @RequestParam("_chunkSize") String _chunkSize,
			@RequestParam("_currentChunkSize") String _currentChunkSize,
			@RequestParam("_chunkNumber") String _chunkNumber, @RequestParam("_totalSize") String _totalSize,
			HttpServletRequest request) {

		try {
			// Servlet3.0方式
			Collection<Part> parts = request.getParts();
			String fileName = null;
			for (Part part : parts) {
				if (part.getContentType() != null) { // 忽略路径字段,只处理文件类型
					fileName = FileUtil.getFileName(part.getHeader("content-disposition"));
					File f = new File(FILE_DIR + fileName + BREAKPOINT + _chunkNumber + "-" + _chunkSize);
					FileUtil.write(part.getInputStream(), f);
				}
			}

			if (StringUtils.isNotEmpty(_currentChunkSize) && StringUtils.isNotEmpty(_chunkSize)
					&& !_chunkSize.equals(_currentChunkSize)) {
				String filePath = FILE_DIR + fileName;
				FileUnifier.mergeFileByChannel(filePath, FileUtil.getAllFileName(FILE_DIR, fileName, BREAKPOINT));
			}
		} catch (Exception e) {
			Logger.error("upload large file error:",	e);
		}
		return "-1";
	}

	/**
	 * 得到文件的上传位置
	 * 
	 * @param fileName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFilePos", method = RequestMethod.GET)
	public Map<String, Integer> getFilePos(@RequestParam("fileName") String fileName) {
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		List<File> list = FileUtil.getAllFileName(FILE_DIR, fileName, BREAKPOINT);
		if (list != null && list.size() > 0) {
			List<String> listName = new ArrayList<>();
			for (File f : list) {
				listName.add(f.getName());
			}
			String lastFileName = listName.get(listName.size() - 1);
			String[] pos = lastFileName.substring(lastFileName.indexOf(BREAKPOINT) + 3).split("-");
			if (pos != null && pos.length == 2) {
				resultMap.put("size", Integer.parseInt(pos[0]) * Integer.parseInt(pos[1]));
			}
		} else {
			resultMap.put("size", 0);
		}
		return resultMap;
	}

	@RequestMapping("download")
	public ResponseEntity<byte[]> download(@RequestBody RequestObj filepath) throws IOException {
		File file = new File(filepath.getFilepath() + "/" + filepath.getFilename());
		Path path = Paths.get(filepath.getFilepath() + "/" + filepath.getFilename());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", filepath.getFilename());
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.add("contentType", Files.probeContentType(path));
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/execption")
	public void execption(String id) throws SystemException {
		if (StringUtils.isEmpty(id)) {
			throw new SystemException("发生系统错误");
		}
	}
}
