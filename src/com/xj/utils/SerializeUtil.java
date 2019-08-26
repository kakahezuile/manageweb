package com.xj.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class SerializeUtil {

	private static Logger logger = Logger.getLogger(SerializeUtil.class);

	public static byte[] serialize(Object value) {
		if (value == null) {
			throw new NullPointerException("Can't serialize null");
		}
		
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream os = new ObjectOutputStream(bos);) {
			os.writeObject(value);
			
			return bos.toByteArray();
		} catch (IOException e) {
			throw new IllegalArgumentException("Non-serializable object", e);
		}
	}

	public static byte[] serializeList(List<Serializable> values) {
		if (values == null || values.size() == 0) {
			throw new NullPointerException("Can't serialize null");
		}
		
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();ObjectOutputStream os = new ObjectOutputStream(bos);) {
			for (Serializable value : values) {
				os.writeObject(value);
			}
			
			return bos.toByteArray();
		} catch (IOException e) {
			throw new IllegalArgumentException("Non-serializable object", e);
		}
	}

	public static Serializable deserialize(byte[] in) {
		if (null == in || in.length == 0) {
			return null;
		}
		
		try (ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(in))) {
			return (Serializable) is.readObject();
		} catch (IOException e) {
			logger.error(String.format("Caught IOException decoding %d bytes of data", in == null ? 0 : in.length), e);
		} catch (ClassNotFoundException e) {
			logger.error(String.format("Caught CNFE decoding %d bytes of data", in == null ? 0 : in.length), e);
		}
		
		return null;
	}

	public static List<Serializable> deserializeList(byte[] in) {
		List<Serializable> list = new ArrayList<>();
		if (null == in || in.length == 0) {
			return list;
		}
		
		try (ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(in))) {
			while (true) {
				Serializable obj = (Serializable) is.readObject();
				if (obj == null) {
					break;
				}
				
				list.add(obj);
			}
		} catch (EOFException e) {
			//it's OK
		} catch (IOException e) {
			logger.error(String.format("Caught IOException decoding %d bytes of data", in == null ? 0 : in.length), e);
		} catch (ClassNotFoundException e) {
			logger.error(String.format("Caught CNFE decoding %d bytes of data", in == null ? 0 : in.length), e);
		}
		
		return list;
	}

}