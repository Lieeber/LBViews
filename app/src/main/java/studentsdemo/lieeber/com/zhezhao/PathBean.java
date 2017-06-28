package studentsdemo.lieeber.com.zhezhao;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by lieeber on 2017/6/28.
 */

public class PathBean {
    public Path path;
    public Paint paint;
    public float width;

    public int color;

    public PathBean(Path path, Paint paint, float width, int color) {
        this.path = path;
        this.paint = paint;
        this.width = width;
        this.color = color;
    }
}
