# AssiduitApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createPresence**](AssiduitApi.md#createPresence) | **POST** /collectivites/{collectiviteId}/activites/{activiteId}/presences | Enregistrer la présence d&#39;un membre |
| [**getAssiduiteMembre**](AssiduitApi.md#getAssiduiteMembre) | **GET** /collectivites/{collectiviteId}/membres/{membreId}/assiduite | Obtenir le taux d&#39;assiduité d&#39;un membre |
| [**listPresences**](AssiduitApi.md#listPresences) | **GET** /collectivites/{collectiviteId}/activites/{activiteId}/presences | Obtenir la feuille de présence d&#39;une activité |
| [**updatePresence**](AssiduitApi.md#updatePresence) | **PATCH** /collectivites/{collectiviteId}/activites/{activiteId}/presences/{membreId} | Mettre à jour la présence d&#39;un membre |


<a id="createPresence"></a>
# **createPresence**
> Presence createPresence(collectiviteId, activiteId, presenceCreate)

Enregistrer la présence d&#39;un membre

Le secrétaire enregistre la présence ou l&#39;excuse d&#39;un membre. Un membre d&#39;une autre collectivité peut être inscrit mais sa présence ne comptera pas dans son taux d&#39;assiduité annuel. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AssiduitApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    AssiduitApi apiInstance = new AssiduitApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer activiteId = 56; // Integer | Identifiant de l'activité
    PresenceCreate presenceCreate = new PresenceCreate(); // PresenceCreate | 
    try {
      Presence result = apiInstance.createPresence(collectiviteId, activiteId, presenceCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AssiduitApi#createPresence");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **collectiviteId** | **Integer**| Identifiant de la collectivité | |
| **activiteId** | **Integer**| Identifiant de l&#39;activité | |
| **presenceCreate** | [**PresenceCreate**](PresenceCreate.md)|  | |

### Return type

[**Presence**](Presence.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Présence enregistrée |  -  |
| **404** | Ressource introuvable |  -  |
| **422** | Entité non traitable — règles métier non respectées |  -  |

<a id="getAssiduiteMembre"></a>
# **getAssiduiteMembre**
> TauxAssiduite getAssiduiteMembre(collectiviteId, membreId, dateDebut, dateFin)

Obtenir le taux d&#39;assiduité d&#39;un membre

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AssiduitApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    AssiduitApi apiInstance = new AssiduitApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer membreId = 56; // Integer | Identifiant du membre
    LocalDate dateDebut = LocalDate.now(); // LocalDate | 
    LocalDate dateFin = LocalDate.now(); // LocalDate | 
    try {
      TauxAssiduite result = apiInstance.getAssiduiteMembre(collectiviteId, membreId, dateDebut, dateFin);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AssiduitApi#getAssiduiteMembre");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **collectiviteId** | **Integer**| Identifiant de la collectivité | |
| **membreId** | **Integer**| Identifiant du membre | |
| **dateDebut** | **LocalDate**|  | |
| **dateFin** | **LocalDate**|  | |

### Return type

[**TauxAssiduite**](TauxAssiduite.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Taux d&#39;assiduité du membre |  -  |
| **404** | Ressource introuvable |  -  |

<a id="listPresences"></a>
# **listPresences**
> List&lt;Presence&gt; listPresences(collectiviteId, activiteId)

Obtenir la feuille de présence d&#39;une activité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AssiduitApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    AssiduitApi apiInstance = new AssiduitApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer activiteId = 56; // Integer | Identifiant de l'activité
    try {
      List<Presence> result = apiInstance.listPresences(collectiviteId, activiteId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AssiduitApi#listPresences");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **collectiviteId** | **Integer**| Identifiant de la collectivité | |
| **activiteId** | **Integer**| Identifiant de l&#39;activité | |

### Return type

[**List&lt;Presence&gt;**](Presence.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Feuille de présence |  -  |
| **404** | Ressource introuvable |  -  |

<a id="updatePresence"></a>
# **updatePresence**
> Presence updatePresence(collectiviteId, activiteId, membreId, presenceUpdate)

Mettre à jour la présence d&#39;un membre

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AssiduitApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    AssiduitApi apiInstance = new AssiduitApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer activiteId = 56; // Integer | Identifiant de l'activité
    Integer membreId = 56; // Integer | Identifiant du membre
    PresenceUpdate presenceUpdate = new PresenceUpdate(); // PresenceUpdate | 
    try {
      Presence result = apiInstance.updatePresence(collectiviteId, activiteId, membreId, presenceUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AssiduitApi#updatePresence");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **collectiviteId** | **Integer**| Identifiant de la collectivité | |
| **activiteId** | **Integer**| Identifiant de l&#39;activité | |
| **membreId** | **Integer**| Identifiant du membre | |
| **presenceUpdate** | [**PresenceUpdate**](PresenceUpdate.md)|  | |

### Return type

[**Presence**](Presence.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Présence mise à jour |  -  |
| **404** | Ressource introuvable |  -  |

