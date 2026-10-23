package com.tianmen.ngz;

import android.content.Context;
import com.tianmen.ngz.data.NgzDatabase;
import com.tianmen.ngz.settings.SettingsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class ProtectViewModel_Factory implements Factory<ProtectViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<NgzDatabase> databaseProvider;

  public ProtectViewModel_Factory(Provider<Context> contextProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<NgzDatabase> databaseProvider) {
    this.contextProvider = contextProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ProtectViewModel get() {
    return newInstance(contextProvider.get(), settingsRepositoryProvider.get(), databaseProvider.get());
  }

  public static ProtectViewModel_Factory create(Provider<Context> contextProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<NgzDatabase> databaseProvider) {
    return new ProtectViewModel_Factory(contextProvider, settingsRepositoryProvider, databaseProvider);
  }

  public static ProtectViewModel newInstance(Context context, SettingsRepository settingsRepository,
      NgzDatabase database) {
    return new ProtectViewModel(context, settingsRepository, database);
  }
}
