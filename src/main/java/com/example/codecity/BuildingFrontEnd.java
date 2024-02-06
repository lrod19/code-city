    package com.example.codecity;

    import javafx.scene.paint.Color;

    /**
     * Class handling the attributes of the front-end graphical representations of the classes
     */

    public class BuildingFrontEnd  {
        private final int x;
        private final int y;
        private final Color color;
        private final int sizeX;
        private final int sizeY;

        /**
         *  Class constructor
         * @param x X-position of building on canvas
         * @param y Y-position of building on canvas
         * @param c Color to draw building
         * @param sizeX Width of building
         * @param sizeY Height of building
         */

        public BuildingFrontEnd(int x, int y, Color c, int sizeX, int sizeY) {
            this.x = x;
            this.y = y;
            this.color = c;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
        }

        /**
         *
         * @return X position of building on screen
         */

        public int getX() {return x;}
        /**
         *
         * @return Y position of building on screen
         */
        public int getY() {return y;}
        /**
         *
         * @return Color of building on screen
         */
        public Color getColor() {return color;}
        /**
         *
         * @return SizeX of building on screen
         */

        public int getSizeX() {return sizeX;}
        /**
         *
         * @return SizeY of building on screen
         */

        public int getSizeY() {return sizeY;}

    }
