package org.samples.blassioli.reddit.features.posts;

import org.samples.blassioli.reddit.ApplicationComponent;
import org.samples.blassioli.reddit.features.posts.data.PostsDataStore;

import dagger.Component;

@Component(
        modules = {PostsModule.class},
        dependencies = ApplicationComponent.class)
public interface PostsComponent {

    PostsPresenter presenter();

    PostsDataStore postsDataStore();

    void inject(PostsFragment fragment);

}
