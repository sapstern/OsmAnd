package net.osmand.plus.settings.backend.backup.items;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.osmand.plus.OsmandApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static net.osmand.plus.backup.ExportBackupTask.APPROXIMATE_ITEM_SIZE_BYTES;

public abstract class CollectionSettingsItem<T> extends SettingsItem {

	protected List<T> items;
	protected List<T> appliedItems;
	protected List<T> duplicateItems;
	protected List<T> existingItems;

	@Override
	protected void init() {
		super.init();
		items = new ArrayList<>();
		appliedItems = new ArrayList<>();
		duplicateItems = new ArrayList<>();
	}

	public CollectionSettingsItem(@NonNull OsmandApplication app, @Nullable CollectionSettingsItem<T> baseItem, @NonNull List<T> items) {
		super(app, baseItem);
		this.items = items;
	}

	public CollectionSettingsItem(@NonNull OsmandApplication app, @NonNull JSONObject json) throws JSONException {
		super(app, json);
	}

	@NonNull
	public List<T> getItems() {
		return items;
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	@NonNull
	public List<T> getAppliedItems() {
		return appliedItems;
	}

	@NonNull
	public List<T> getDuplicateItems() {
		return duplicateItems;
	}

	@NonNull
	public List<T> processDuplicateItems() {
		if (!items.isEmpty()) {
			for (T item : items) {
				if (isDuplicate(item)) {
					duplicateItems.add(item);
				}
			}
		}
		return duplicateItems;
	}

	public List<T> getNewItems() {
		List<T> res = new ArrayList<>(items);
		res.removeAll(duplicateItems);
		return res;
	}

	@Override
	public long getEstimatedSize() {
		long size = 0;
		for (T item : items) {
			size += getEstimatedItemSize(item);
		}
		return size + APPROXIMATE_ITEM_SIZE_BYTES;
	}

	public boolean shouldShowDuplicates() {
		return true;
	}

	@Override
	public boolean shouldReadOnCollecting() {
		return true;
	}

	public abstract boolean isDuplicate(@NonNull T item);

	@NonNull
	public abstract T renameItem(@NonNull T item);

	public abstract long getEstimatedItemSize(@NonNull T item);
}
