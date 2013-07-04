package gpu.api;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;

public class GPUApi {

    public static void main(String[] _args) {

        final int size = 1024 * 1024 * 16;

        final float[] a = new float[size];
        final float[] b = new float[size];

        for (int i = 0; i < size; i++) {
            a[i] = (float) (Math.random() * 100);
            b[i] = (float) (Math.random() * 100);
        }
        
        final float[] sum = new float[size];
        
        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                int gid = getGlobalId();
                sum[gid] = a[gid] + b[gid];
            }
        };
        kernel.setExecutionMode(Kernel.EXECUTION_MODE.GPU);
        kernel.execute(Range.create(512));
        System.out.println(kernel.getExecutionMode());
        System.out.println(kernel.getExecutionTime() + " ms.");

        kernel.dispose();
    }
}
