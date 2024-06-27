package com.example.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import demo.example.service.ProgressHandler;

@RestController
//@RequestMapping("/")
public class FileUploadController {

	@Autowired
	private ProgressHandler progressHandler;

	@GetMapping("/sample")
	public ResponseEntity<?> temp() {
		return ResponseEntity.ok("msg");
	}

	@PostMapping("/upload")
	@ResponseBody
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("sessionId") String sessionId) {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			System.out.println(sessionId);
			int totalLines = 0;
			while ((line = reader.readLine()) != null) {
				totalLines++;
			}

			reader.close();

			int processedLines = 0;
			BufferedReader progressReader = new BufferedReader(
					new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
			while ((line = progressReader.readLine()) != null) {
				processedLines++;
				double progress = (double) processedLines / totalLines * 100;
				progressHandler.sendProgressUpdate(sessionId, progress);
			}

			return "File uploaded successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "File upload failed";
		}
	}
}
