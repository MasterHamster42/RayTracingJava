package raytacer;

public class SkyBox {
    Texture texture;

    public void loadSkyBox(String path){
        texture = new ImageTexture(path);
    }

    public void loadSkyBox(ColorRGB colorRGB){
        texture =  new SolidColorTexture(colorRGB);
    }

    public ColorRGB returnColorAt(Vector3 direction){
        if (texture.getClass().equals(SolidColorTexture.class)){return ((SolidColorTexture)texture).returnColor();}

        int u =1, v =1;
        int cubeLen = texture.getWidth()/4;
        double[] uvf = convert_xyz_to_cube_uv(direction.normalizeThis());
        switch ((int) uvf[2]){
            case 0:
                u = 2*cubeLen + (int) (cubeLen*uvf[0]);
                v = cubeLen + (int)(cubeLen*uvf[1]);
                break;
            case 1:
                u = (int) (cubeLen*uvf[0]);
                v = cubeLen + (int)(cubeLen*uvf[1]);
                break;
            case 2:
                u = cubeLen + (int) (cubeLen*uvf[0]);
                v = 2*cubeLen + (int)(cubeLen*uvf[1]);
                break;
            case 3:
                u = cubeLen + (int) (cubeLen*uvf[0]);
                v = (int)(cubeLen*uvf[1]);
                break;
            case 4:
                u = cubeLen + (int) (cubeLen*uvf[0]);
                v = cubeLen + (int)(cubeLen*uvf[1]);
            break;
            case 5:
                u = 3*cubeLen + (int) (cubeLen*uvf[0]);
                v = cubeLen + (int)(cubeLen*uvf[1]);

        }

        return texture.returnColor(u, texture.getHeight() - v);
    }


    private double[] convert_xyz_to_cube_uv(Vector3 direction)
    {
        double absX = Math.abs(direction.x);
        double absY = Math.abs(direction.y);
        double absZ = Math.abs(direction.z);

        boolean isXPositive = direction.x > 0;
        boolean isYPositive = direction.y > 0;
        boolean isZPositive = direction.z > 0;

        double maxAxis=1, uc=1, vc=1;
        int index = 0;

        // POSITIVE X
        if (isXPositive && absX >= absY && absX >= absZ) {
            maxAxis = absX;
            uc = -direction.z;
            vc = direction.y;
            index = 0;
        }
        // NEGATIVE X
        if (!isXPositive && absX >= absY && absX >= absZ) {
            // u (0 to 1) goes from -z to +z
            // v (0 to 1) goes from -y to +y
            maxAxis = absX;
            uc = direction.z;
            vc = direction.y;
            index = 1;
        }
        // POSITIVE Y
        if (isYPositive && absY >= absX && absY >= absZ) {
            // u (0 to 1) goes from -x to +x
            // v (0 to 1) goes from +z to -z
            maxAxis = absY;
            uc = direction.x;
            vc = -direction.z;
            index = 2;
        }
        // NEGATIVE Y
        if (!isYPositive && absY >= absX && absY >= absZ) {
            // u (0 to 1) goes from -x to +x
            // v (0 to 1) goes from -z to +z
            maxAxis = absY;
            uc = direction.x;
            vc = direction.z;
            index = 3;
        }
        // POSITIVE Z
        if (isZPositive && absZ >= absX && absZ >= absY) {
            // u (0 to 1) goes from -x to +x
            // v (0 to 1) goes from -y to +y
            maxAxis = absZ;
            uc = direction.x;
            vc = direction.y;
            index = 4;
        }
        // NEGATIVE Z
        if (!isZPositive && absZ >= absX && absZ >= absY) {
            // u (0 to 1) goes from +x to -x
            // v (0 to 1) goes from -y to +y
            maxAxis = absZ;
            uc = -direction.x;
            vc = direction.y;
            index = 5;
        }

        // Convert range from -1 to 1 to 0 to 1
        double u = 0.5f * (uc / maxAxis + 1.0f);
        double v = 0.5f * (vc / maxAxis + 1.0f);
        return new double[]{u, v, index};
    }
}
