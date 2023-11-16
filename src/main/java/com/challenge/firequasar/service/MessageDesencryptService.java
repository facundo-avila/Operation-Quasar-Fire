package com.challenge.firequasar.service;

import java.util.List;

import com.challenge.firequasar.exception.ServiceException;

public interface MessageDesencryptService {

	String getMessage(List<String>... messages) throws ServiceException;
}
