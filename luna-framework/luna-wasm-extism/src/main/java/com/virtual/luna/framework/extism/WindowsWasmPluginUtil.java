package com.virtual.luna.framework.extism;

import org.extism.sdk.Plugin;
import org.extism.sdk.manifest.Manifest;
import org.extism.sdk.wasm.PathWasmSource;
import org.extism.sdk.wasm.UrlWasmSource;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class WindowsWasmPluginUtil {

    static {
        // 设置 JNA 库的路径，从资源目录中加载
        System.setProperty("jna.library.path", getLibraryPath());
    }

    /**
     * 获取 JNA 库的路径
     *
     * @return JNA 库的路径字符串
     */
    private static String getLibraryPath() {
        // 假设库文件放置在 resources 目录下
        URL resource = WindowsWasmPluginUtil.class.getClassLoader().getResource("./windows/libextism-x86_64-pc-windows-msvc-v1.3.0");
        Objects.requireNonNull(resource, "在资源中未找到库路径");
        return resource.getPath().toString();
    }

    /**
     * 从文件中调用 WASM 函数
     *
     * @param functionName 要调用的函数名
     * @param input        函数的输入参数
     * @return 函数调用的结果
     * @throws IOException 如果读取文件或调用插件时发生 I/O 错误
     */
    public static String callWasmFunctionFromFile(String file ,String functionName, String input) throws IOException {
        // 假设 WASM 文件放置在 resources 目录下
        URL resource = WindowsWasmPluginUtil.class.getClassLoader().getResource(file);
        Objects.requireNonNull(resource, "在资源中未找到 WASM 文件");

        var pathWasmSource = new PathWasmSource((String) null, resource.getPath(), (String) null);
        var manifest = new Manifest(List.of(pathWasmSource));
        var plugin = new Plugin(manifest, false, null);

        return plugin.call(functionName, input);
    }

    /**
     * 从 URL 中调用 WASM 函数
     *
     * @param functionName 要调用的函数名
     * @param input        函数的输入参数
     * @param url          包含 WASM 文件的 URL
     * @return 函数调用的结果
     * @throws IOException 如果读取 URL 或调用插件时发生 I/O 错误
     */
    public static String callWasmFunctionFromUrl(String functionName, String input, String url) throws IOException {
        var manifest = new Manifest(List.of(UrlWasmSource.fromUrl(url)));
        var plugin = new Plugin(manifest, false, null);

        return plugin.call(functionName, input);
    }

    public static void main(String[] args) {
        try {
            // 从文件中调用 WASM 函数的示例
//            String resultFromFile = callWasmFunctionFromFile("windows/wasm/rust_pdk_template.wasm","greet", "Hello, World!");
//            System.out.println("文件中的结果：" + resultFromFile);

            // 从 URL 中调用 WASM 函数的示例
            String url = "https://ydkt-data-ekwclass-com.oss-cn-qingdao.aliyuncs.com/wasm/rust_pdk_template.wasm";
            String resultFromUrl = callWasmFunctionFromUrl("greet", "Hello, World!", url);
            System.out.println("URL 中的结果：" + resultFromUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

