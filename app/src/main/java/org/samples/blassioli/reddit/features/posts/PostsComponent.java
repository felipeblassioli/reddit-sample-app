package org.samples.blassioli.reddit.features.posts;

import org.samples.blassioli.reddit.ApplicationComponent;

import dagger.Component;

@Component(
        modules = {PostsModule.class},
        dependencies = ApplicationComponent.class)
public interface PostsComponent {

    PostsPresenter presenter();

    void inject(PostsFragment fragment);

}
