package net.osmand.plus.inapp;

import androidx.annotation.NonNull;

import net.osmand.plus.OsmandApplication;
import net.osmand.plus.Version;
import net.osmand.plus.settings.backend.backup.exporttype.ExportType;
import net.osmand.plus.views.mapwidgets.WidgetType;

import java.util.Calendar;

public class InAppPurchaseUtils {

	public static final int HUGEROCK_PROMO_MONTHS = 6;
	public static final int TRIPLTEK_PROMO_MONTHS = 12;
	private static final long ANDROID_AUTO_START_DATE_MS = 10L * 1000L * 60L * 60L * 24L; // 10 days


	protected static boolean isFullVersionPurchased(@NonNull OsmandApplication app) {
		return true;
	}

	protected static boolean isLiveUpdatesPurchased(@NonNull OsmandApplication app) {
		return true;
	}

	protected static boolean isMapsPlusPurchased(@NonNull OsmandApplication app) {
		return true;
	}

	protected static boolean isOsmAndProPurchased(@NonNull OsmandApplication app) {
		return true;
	}

	protected static boolean isContourLinesPurchased(@NonNull OsmandApplication app) {
		return true;
	}

	protected static boolean isDepthContoursPurchased(@NonNull OsmandApplication app) {
		return true;
	}

	protected static boolean isPromoSubscribed(@NonNull OsmandApplication app) {
		return true;
	}

	protected static boolean isMapperUpdatesSubscribed(@NonNull OsmandApplication app) {
		return true;
	}


	public static boolean isFullVersionAvailable(@NonNull OsmandApplication app) {
		return isFullVersionAvailable(app, true);
	}

	public static boolean isFullVersionAvailable(@NonNull OsmandApplication app, boolean checkDevBuild) {
		return isFullVersionPurchased(app) || checkDeveloperBuildIfNeeded(app, checkDevBuild);
	}

	public static boolean isMapsPlusAvailable(@NonNull OsmandApplication app) {
		return isMapsPlusAvailable(app, true);
	}

	public static boolean isMapsPlusAvailable(@NonNull OsmandApplication app, boolean checkDevBuild) {
		return isMapsPlusPurchased(app) || checkDeveloperBuildIfNeeded(app, checkDevBuild);
	}

	public static boolean isOsmAndProAvailable(@NonNull OsmandApplication app) {
		return isOsmAndProAvailable(app, true);
	}

	public static boolean isOsmAndProAvailable(@NonNull OsmandApplication app, boolean checkDevBuild) {
		return isOsmAndProPurchased(app) || isPromoSubscribed(app) || checkDeveloperBuildIfNeeded(app, checkDevBuild);
	}

	private static boolean checkDeveloperBuildIfNeeded(@NonNull OsmandApplication app, boolean shouldCheck) {
		return shouldCheck && Version.isDeveloperBuild(app);
	}

	public static boolean isSubscribedToAny(@NonNull OsmandApplication app) {
		return true;
	}

	public static boolean isSubscribedToAny(@NonNull OsmandApplication app, boolean checkDevBuild) {
		return true;
	}

	public static boolean isLiveUpdatesAvailable(@NonNull OsmandApplication app) {
		return true;
	}

	public static boolean isWidgetPurchased(@NonNull OsmandApplication app, @NonNull WidgetType wt) {
		if (wt.isProWidget()) {
			return wt.isOBDWidget() ? isVehicleMetricsAvailable(app) : isProWidgetsAvailable(app);
		}
		return true;
	}

	public static boolean isVehicleMetricsAvailable(@NonNull OsmandApplication app) {
		return isOsmAndProAvailable(app);
	}

	public static boolean isProWidgetsAvailable(@NonNull OsmandApplication app) {
		return true;
	}

	public static boolean is3dMapsAvailable(@NonNull OsmandApplication app) {
		return true;
	}

	public static boolean isExportTypeAvailable(@NonNull OsmandApplication app,
	                                            @NonNull ExportType exportType) {
		return true;
	}

	public static boolean isBackupAvailable(@NonNull OsmandApplication app) {
		return true;
	}

	public static boolean isWeatherAvailable(@NonNull OsmandApplication app) {
		return true;
	}

	public static boolean isColoringTypeAvailable(@NonNull OsmandApplication app) {
		return true;
	}

	public static boolean isDepthContoursAvailable(@NonNull OsmandApplication app) {
		return true;
	}

	public static boolean isContourLinesAvailable(@NonNull OsmandApplication app) {
		return true;
	}

	public static boolean isAndroidAutoAvailable(@NonNull OsmandApplication app) {
		return true;
	}

	public static boolean isTripltekPromoAvailable(@NonNull OsmandApplication app) {
		return true;
	}

	public static long getTripltekPromoExpirationTime(@NonNull OsmandApplication app) {
		if (Version.isTripltekBuild()) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Version.getInstallTime(app));
			calendar.add(Calendar.MONTH, TRIPLTEK_PROMO_MONTHS);

			return calendar.getTimeInMillis();
		}
		return 0;
	}

	public static boolean isHugerockPromoAvailable(@NonNull OsmandApplication app) {
		return true;
	}

	public static long getHugerockPromoExpirationTime(@NonNull OsmandApplication app) {
		if (Version.isHugerockBuild()) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Version.getInstallTime(app));
			calendar.add(Calendar.MONTH, HUGEROCK_PROMO_MONTHS);

			return calendar.getTimeInMillis();
		}
		return 0;
	}
}