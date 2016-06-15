/*
 *    The MIT License (MIT)
 *
 *    Copyright (c) 2016 Marco Gomiero
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *    copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all
 *    copies or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 *    THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *    SOFTWARE.
 */

package com.prof.youtubeparser.models;

/**
 * Created by marco on 5/7/16.
 */
public class Thumbnails {

    private Default _default;
    private Medium medium;
    private High high;

    /**
     *
     * @return
     *     The _default
     */
    public Default getDefault() {
        return _default;
    }

    /**
     *
     * @param _default
     *     The default
     */
    public void setDefault(Default _default) {
        this._default = _default;
    }

    /**
     *
     * @return
     *     The medium
     */
    public Medium getMedium() {
        return medium;
    }

    /**
     *
     * @param medium
     *     The medium
     */
    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    /**
     *
     * @return
     *     The high
     */
    public High getHigh() {
        return high;
    }

    /**
     *
     * @param high
     *     The high
     */
    public void setHigh(High high) {
        this.high = high;
    }

    @Override
    public String toString() {
        return "Thumbnails{" +
                "_default=" + _default +
                ", medium=" + medium +
                ", high=" + high +
                '}';
    }

}
