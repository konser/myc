package test.udp;

import java.util.ArrayList;
import java.util.List;


public class ClientInfo {
	public String addr;
	public int port;
	public ClientInfo(String addr, int port) {
		super();
		this.addr = addr;
		this.port = port;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addr == null) ? 0 : addr.hashCode());
		result = prime * result + port;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientInfo other = (ClientInfo) obj;
		if (addr == null) {
			if (other.addr != null)
				return false;
		} else if (!addr.equals(other.addr))
			return false;
		if (port != other.port)
			return false;
		return true;
	}
	public String toStringItem() {
		return addr + "|" + port;
	}
	public static List<ClientInfo> resolve(String str) {
		List<ClientInfo> result = new ArrayList<ClientInfo>();
		if(str == null || str.isEmpty()) {
			return result;
		}
		String[] arr = str.split(";");
		for(String item:arr) {
			if(item == null || item.isEmpty()) {
				continue;
			}
			String[] arr0 = item.split("[|]");
			if(arr0 != null && arr0.length > 1) {
				result.add(new ClientInfo(arr0[0], Integer.parseInt(arr0[1])));
			}
		}
		return result;
	}
	
	public static String getClientInfos(List<ClientInfo> clientInfos) {
		StringBuilder result = new StringBuilder();
		for (ClientInfo item : clientInfos) {
			result.append(item.toStringItem());
			result.append(";");
		}
		return result.toString();
	}
	@Override
	public String toString() {
		return "ClientInfo [addr=" + addr + ", port=" + port + "]";
	}
}