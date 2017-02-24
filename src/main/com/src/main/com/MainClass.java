package main.com.src.main.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.com.utils.ConnectionManager;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConnectionManager conman= new ConnectionManager();
		Map<String, String> VRStatus= new HashMap<String, String>();
		VRStatus = conman.GetMappedStatusFromVR();
		System.out.println(VRStatus);
	}

}
