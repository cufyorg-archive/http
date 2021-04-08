# org.cufyx.http [![](https://jitpack.io/v/org.cufyx/http.svg)](https://jitpack.io/#org.cufyx/http)
 Extra components of the [`org.cufy.http`](https://github.com/cufyorg/http) for comfort in android.

# Import (Gradle)

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    //import the `org.cufy.http` library
    //replace TAG with the needed version.
    implementation 'org.cufy:http:TAG'

    //import this library
    //replace TAG with the needed version.
    implementation 'org.cufyx:http:TAG'
}
```

# Example

```java 
XClient.defaultClient(context)
    .request(r -> r
        .uri("http://example.com")
    )
    .onh(Client.CONNECTED, (client, response) -> {
        //already in UI thread
        Toast.makeText(client.context(), response.reasonPhrase().toString(), Toast.LENGTH_LONG).show();
    })
    .onh(Client.DISCONNECTED, (client, exception) -> {
        Toast.makeText(client.context(), exception.getMessage(), Toast.LENGTH_LONG).show();
    })
    .connect();
```

# Contact Info

- E-Mail: lsafer@cufy.org

# LICENCE

```
Copyright 2021 Cufy
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
