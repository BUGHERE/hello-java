class Solution {

	public String[] splitMessage(String message, int limit) {
		for (int i = 1, j = 1; i <= message.length(); j += ("" + ++i).length()) {
			if ((3 + ("" + i).length()) * i + j + message.length() <= limit * i) {
				String[] result = new String[i];
				for (int k = 1, m = 0; k <= i; k++) {
					result[k - 1] = message.substring(m,
							Math.min(message.length(), m += Math.max(0, limit - 3 - ("" + i + k).length()))) + '<' + k
							+ '/' + i + '>';
				}
				return result;
			}
		}
		return new String[0];
	}
}