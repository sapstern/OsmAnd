package net.osmand.plus.backup.commands;

import static net.osmand.plus.backup.BackupHelper.ACCOUNT_DELETE_URL;
import static net.osmand.plus.backup.BackupHelper.STATUS_EMPTY_RESPONSE_ERROR;
import static net.osmand.plus.backup.BackupHelper.STATUS_SERVER_ERROR;
import static net.osmand.plus.backup.BackupHelper.STATUS_SUCCESS;

import androidx.annotation.NonNull;

import net.osmand.OperationLog;
import net.osmand.plus.backup.BackupCommand;
import net.osmand.plus.backup.BackupError;
import net.osmand.plus.backup.BackupHelper;
import net.osmand.plus.backup.BackupListeners.OnDeleteAccountListener;
import net.osmand.plus.utils.AndroidNetworkUtils;
import net.osmand.plus.utils.AndroidNetworkUtils.OnRequestResultListener;
import net.osmand.util.Algorithms;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteAccountCommand extends BackupCommand {

	private final String email;
	private final String token;

	public DeleteAccountCommand(@NonNull BackupHelper helper, @NonNull String email, @NonNull String token) {
		super(helper);
		this.email = email;
		this.token = token;
	}

	@NonNull
	public List<OnDeleteAccountListener> getListeners() {
		return getHelper().getBackupListeners().getDeleteAccountListeners();
	}


	@Override
	protected Object doInBackground(Object... objects) {
		String deviceId = getHelper().getDeviceId();
		Map<String, String> parameters = new HashMap<>();
		parameters.put("deviceid", deviceId);
		parameters.put("accessToken", getHelper().getAccessToken());

		JSONObject json = new JSONObject();
		try {
			json.put("username", email);
			json.put("password", JSONObject.NULL);
			json.put("token", token);
		} catch (JSONException e) {

		}

		String body = json.toString();
		String operation = "Account Delete";
		String contentType = "application/json";
		OnRequestResultListener listener = getRequestListener(deviceId);
		String url = ACCOUNT_DELETE_URL + "?" + AndroidNetworkUtils.getParameters(getApp(), parameters, listener, operation, true);

		AndroidNetworkUtils.sendRequest(getApp(), url, body, operation, contentType, true, true, listener);

		return null;
	}

	@NonNull
	private OnRequestResultListener getRequestListener(String deviceId) {
		OperationLog operationLog = createOperationLog("accountDelete");

		return (result, error, resultCode) -> {
			int status;
			String message = null;
			BackupError backupError = null;
			if (!Algorithms.isEmpty(error)) {
				backupError = new BackupError(error);
				message = "Account deletion error: " + backupError + "\nEmail=" + email + "\nDeviceId=" + deviceId;
				status = STATUS_SERVER_ERROR;
			} else if (resultCode != null && resultCode == HttpURLConnection.HTTP_OK) {
				message = result;
				status = STATUS_SUCCESS;
			} else {
				message = "Account deletion error: empty response";
				status = STATUS_EMPTY_RESPONSE_ERROR;
			}
			publishProgress(status, message, backupError);
			operationLog.finishOperation(status + " " + message);
		};
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		for (OnDeleteAccountListener listener : getListeners()) {
			int status = (Integer) values[0];
			String message = (String) values[1];
			BackupError backupError = (BackupError) values[2];
			listener.onDeleteAccount(status, message, backupError);
		}
	}
}