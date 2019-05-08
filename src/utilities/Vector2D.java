package utilities;

public final class Vector2D {
    public double x, y;

    // constructor for zero vector
    public Vector2D() { x = 0; y = 0; }

    // constructor for vector with given coordinates
    public Vector2D(double x, double y) { this.x = x; this.y = y; }

    // constructor that copies the argument vector
    public Vector2D(Vector2D v) { this.x = v.x; this.y = v.y; }

    // set coordinates
    public Vector2D set(double x, double y) { this.x = x; this.y = y; return this; }

    // set coordinates based on argument vector
    public Vector2D set(Vector2D v) { this.x = v.x; this.y = v.y; return this; }

    // compare for equality (note Object type argument)
    public boolean equals(Object o) {
        Vector2D v = (Vector2D)o;
        return (this.x==v.x && this.y==v.y);
    }

    // String for displaying vector as text
    public String toString() { return "Vector has x coordinate "+this.x+" and y coordinate "+this.y;}

    //  magnitude (= "length") of this vector
    public double mag() { return Math.hypot(this.x, this.y); }

    // angle between vector and horizontal axis in radians
    public double angle() { return Math.atan2(this.y, this.x); }

    // angle between this vector and another vector
    public double angle(Vector2D other) {
        double angle = other.angle()-this.angle();
        if ( angle < 0 ) { angle+=2*Math.PI; }
        return angle;
    }

    // add argument vector
    public Vector2D add(Vector2D v) { this.x+=v.x; this.y+=v.y; return this; }

    // add values to coordinates
    public Vector2D add(double x, double y) { this.x+=x; this.y+=y; return this; }

    // weighted add - surprisingly useful
    public Vector2D addScaled(Vector2D v, double fac) { this.x+=(v.x)*fac; this.y+=(v.y)*fac; return this; }

    // subtract argument vector
    public Vector2D subtract(Vector2D v) { this.x-=v.x; this.y-=v.y; return this; }

    // subtract values from coordinates
    public Vector2D subtract(double x, double y) { this.x-=x; this.y-=y; return this; }

    // multiply with factor
    public Vector2D mult(double fac) { this.x*=fac; this.y*=fac; return this;}

    // rotate by angle given in radians
    public Vector2D rotate(double angle) {
        double x1, y1;
        x1 = this.x*Math.cos(angle) - this.y*Math.sin(angle);
        y1 = this.x*Math.sin(angle) + this.y*Math.cos(angle);
        this.x = x1; this.y = y1; return this;
    }

    // "dot product" ("scalar product") with argument vector
    public double dot(Vector2D v) { return this.x*v.x + this.y*v.y;}

    // distance to argument vector
    public double dist(Vector2D v) {
        return Math.hypot(Math.abs(v.x-this.x), Math.abs(v.y-this.y));
    }

    // normalise vector so that magnitude becomes 1
    public Vector2D normalise() {
        double mag = this.mag();
        this.x/=mag;
        this.y/=mag;
        return this;
    }

    // wrap-around operation, assumes w> 0 and h>0
    public Vector2D wrap(double w, double h) {
        if ( this.x > w ) { this.x -= w; }
        if ( this.y > h ) { this.y -= h; }
        if ( this.x < 0 ) { this.x += w; }
        if ( this.y < 0 ) { this.y += h; }
        return this;
    }

    // construct vector with given polar coordinates
    public static Vector2D polar(double angle, double mag) {
        return new Vector2D(Math.cos(angle)*mag, Math.sin(angle)*mag);

    }

}
