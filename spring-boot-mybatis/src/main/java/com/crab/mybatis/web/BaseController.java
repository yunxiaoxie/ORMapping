package com.crab.mybatis.web;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.crab.mybatis.domain.RequestObj;
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

	@Autowired
	private SysDataDicService service;

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
					System.out.println(fileName);
					File f = new File("d:/images/" + fileName);
					FileUtil.write(part.getInputStream(), f);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-1";
	}

	@RequestMapping("download")
	public ResponseEntity<byte[]> download(@RequestBody RequestObj filepath) throws IOException {
		File file = new File(filepath.getFilepath() + "/" + filepath.getFilename());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", filepath.getFilename());
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}
}
