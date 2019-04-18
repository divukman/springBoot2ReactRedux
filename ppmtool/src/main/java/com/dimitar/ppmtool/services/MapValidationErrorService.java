package com.dimitar.ppmtool.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidationErrorService {

	/**
	 * Util method for validation of the binding result.
	 * @param bindingResult
	 * @return A response entity holding a map of key: value pairs (property name: error message).
	 */
	public ResponseEntity<?> validateBindingResult(final BindingResult bindingResult) {
		ResponseEntity<Map<String, String>> result = null;

		if ( (bindingResult != null) && bindingResult.hasErrors()) {
			final Map<String, String> errMap = new HashMap<String, String>();
			for (FieldError err: bindingResult.getFieldErrors()) {
				errMap.put(err.getField(), err.getDefaultMessage());
			}
			result = new ResponseEntity<Map<String,String>>(errMap, HttpStatus.BAD_REQUEST) ;
		}

		return result;
	}

}
