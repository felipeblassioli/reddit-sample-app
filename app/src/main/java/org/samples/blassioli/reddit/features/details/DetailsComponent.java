package org.samples.blassioli.reddit.features.details;

import org.samples.blassioli.reddit.ApplicationComponent;
import org.samples.blassioli.reddit.features.details.DetailsFragment;
import org.samples.blassioli.reddit.features.details.DetailsModule;
import org.samples.blassioli.reddit.features.details.DetailsPresenter;

import dagger.Component;

@Component(modules = { DetailsModule.class }, dependencies = ApplicationComponent.class)
public interface DetailsComponent {

    DetailsPresenter presenter();

    void inject(DetailsFragment fragment);

}
