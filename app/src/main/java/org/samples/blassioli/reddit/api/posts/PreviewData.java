package org.samples.blassioli.reddit.api.posts;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class PreviewData {
    public List<PreviewDataImage> images;
    public boolean enabled;
}
