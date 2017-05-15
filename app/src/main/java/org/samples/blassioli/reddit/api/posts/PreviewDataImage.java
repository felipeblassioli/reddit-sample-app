package org.samples.blassioli.reddit.api.posts;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class PreviewDataImage {
    public PreviewDataImageSource source;
    public List<PreviewDataImageSource> resolutions;
}
