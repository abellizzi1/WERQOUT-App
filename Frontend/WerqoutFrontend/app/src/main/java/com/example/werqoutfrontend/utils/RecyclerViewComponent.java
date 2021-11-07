package com.example.werqoutfrontend.utils;

/**
 * A class that represents the components that are displayed in the recycler view
 */
public class RecyclerViewComponent {
    /**
     * The image that is displayed next in a component
     */
    private int mImageResource;
    /**
     * The main text of a component
     */
    private String mText1;
    /**
     * The subtext of a component
     */
    private String mText2;
    /**
     * Stores the original each component within a list even when the filter may switch it's position
     */
    private final int mOriginalIndex;

    /**
     * Creates a new component to be displayed within a recycler view
     * @param ImageResource
     *  The image that is displayed next in a component
     * @param text1
     *  The main text of a component
     * @param text2
     *  The subtext of a component
     * @param originalIndex
     *  Stores the original each component within a list
     */
    public RecyclerViewComponent(int ImageResource, String text1, String text2, int originalIndex)
    {
        mImageResource = ImageResource;
        mText1 = text1;
        mText2 = text2;
        mOriginalIndex = originalIndex;
    }

    /**
     * Gets the image of the component
     * @return
     *  The image of the component
     */
    public int getImageResource() {
        return mImageResource;
    }

    /**
     * Gets the main text of the component
     * @return
     *  The main text of the component
     */
    public String getText1() {
        return mText1;
    }

    /**
     * Gets the subtext of the component
     * @return
     *  The subtext of the component
     */
    public String getText2() {
        return mText2;
    }

    /**
     * Gets the original index of the component
     * @return
     *  The original index of the component
     */
    public int getOriginalIndex() {
        return mOriginalIndex;
    }
}
