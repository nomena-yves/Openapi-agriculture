# MembresApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**admettreMembreCollectivite**](MembresApi.md#admettreMembreCollectivite) | **POST** /collectivites/{collectiviteId}/membres | Admettre un nouveau membre dans une collectivité |
| [**demissionMembre**](MembresApi.md#demissionMembre) | **POST** /collectivites/{collectiviteId}/membres/{membreId}/demission | Enregistrer la démission d&#39;un membre |
| [**getMembre**](MembresApi.md#getMembre) | **GET** /collectivites/{collectiviteId}/membres/{membreId} | Obtenir un membre |
| [**listMembres**](MembresApi.md#listMembres) | **GET** /collectivites/{collectiviteId}/membres | Lister les membres d&#39;une collectivité |
| [**transfererMembre**](MembresApi.md#transfererMembre) | **POST** /collectivites/{collectiviteId}/membres/{membreId}/transfert | Transférer un membre vers une autre collectivité |
| [**updateMembre**](MembresApi.md#updateMembre) | **PATCH** /collectivites/{collectiviteId}/membres/{membreId} | Mettre à jour les informations d&#39;un membre |


<a id="admettreMembreCollectivite"></a>
# **admettreMembreCollectivite**
> Membre admettreMembreCollectivite(collectiviteId, membreAdmission)

Admettre un nouveau membre dans une collectivité

Conditions : parrainage par un membre confirmé avec plus de 90 jours d&#39;ancienneté, informations personnelles complètes, frais d&#39;adhésion de 50 000 MGA réglés. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MembresApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    MembresApi apiInstance = new MembresApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    MembreAdmission membreAdmission = new MembreAdmission(); // MembreAdmission | 
    try {
      Membre result = apiInstance.admettreMembreCollectivite(collectiviteId, membreAdmission);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MembresApi#admettreMembreCollectivite");
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
| **membreAdmission** | [**MembreAdmission**](MembreAdmission.md)|  | |

### Return type

[**Membre**](Membre.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Membre admis |  -  |
| **400** | Requête invalide |  -  |
| **404** | Ressource introuvable |  -  |
| **422** | Entité non traitable — règles métier non respectées |  -  |

<a id="demissionMembre"></a>
# **demissionMembre**
> Membre demissionMembre(collectiviteId, membreId, demissionMembreRequest)

Enregistrer la démission d&#39;un membre

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MembresApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    MembresApi apiInstance = new MembresApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer membreId = 56; // Integer | Identifiant du membre
    DemissionMembreRequest demissionMembreRequest = new DemissionMembreRequest(); // DemissionMembreRequest | 
    try {
      Membre result = apiInstance.demissionMembre(collectiviteId, membreId, demissionMembreRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MembresApi#demissionMembre");
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
| **demissionMembreRequest** | [**DemissionMembreRequest**](DemissionMembreRequest.md)|  | |

### Return type

[**Membre**](Membre.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Démission enregistrée |  -  |
| **404** | Ressource introuvable |  -  |

<a id="getMembre"></a>
# **getMembre**
> Membre getMembre(collectiviteId, membreId)

Obtenir un membre

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MembresApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    MembresApi apiInstance = new MembresApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer membreId = 56; // Integer | Identifiant du membre
    try {
      Membre result = apiInstance.getMembre(collectiviteId, membreId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MembresApi#getMembre");
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

### Return type

[**Membre**](Membre.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Détail d&#39;un membre |  -  |
| **404** | Ressource introuvable |  -  |

<a id="listMembres"></a>
# **listMembres**
> ListMembres200Response listMembres(collectiviteId, page, limit, statut, poste)

Lister les membres d&#39;une collectivité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MembresApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    MembresApi apiInstance = new MembresApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer page = 1; // Integer | 
    Integer limit = 20; // Integer | 
    String statut = "actif"; // String | Filtrer par statut
    PosteEnum poste = PosteEnum.fromValue("president"); // PosteEnum | 
    try {
      ListMembres200Response result = apiInstance.listMembres(collectiviteId, page, limit, statut, poste);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MembresApi#listMembres");
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
| **page** | **Integer**|  | [optional] [default to 1] |
| **limit** | **Integer**|  | [optional] [default to 20] |
| **statut** | **String**| Filtrer par statut | [optional] [enum: actif, demissionne] |
| **poste** | [**PosteEnum**](.md)|  | [optional] [enum: president, president_adjoint, tresorier, secretaire, membre_confirme, membre_junior] |

### Return type

[**ListMembres200Response**](ListMembres200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des membres |  -  |
| **404** | Ressource introuvable |  -  |

<a id="transfererMembre"></a>
# **transfererMembre**
> Membre transfererMembre(collectiviteId, membreId, transfererMembreRequest)

Transférer un membre vers une autre collectivité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MembresApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    MembresApi apiInstance = new MembresApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer membreId = 56; // Integer | Identifiant du membre
    TransfererMembreRequest transfererMembreRequest = new TransfererMembreRequest(); // TransfererMembreRequest | 
    try {
      Membre result = apiInstance.transfererMembre(collectiviteId, membreId, transfererMembreRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MembresApi#transfererMembre");
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
| **transfererMembreRequest** | [**TransfererMembreRequest**](TransfererMembreRequest.md)|  | |

### Return type

[**Membre**](Membre.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Transfert effectué |  -  |
| **404** | Ressource introuvable |  -  |
| **422** | Entité non traitable — règles métier non respectées |  -  |

<a id="updateMembre"></a>
# **updateMembre**
> Membre updateMembre(collectiviteId, membreId, membreUpdate)

Mettre à jour les informations d&#39;un membre

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MembresApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    MembresApi apiInstance = new MembresApi(defaultClient);
    Integer collectiviteId = 56; // Integer | Identifiant de la collectivité
    Integer membreId = 56; // Integer | Identifiant du membre
    MembreUpdate membreUpdate = new MembreUpdate(); // MembreUpdate | 
    try {
      Membre result = apiInstance.updateMembre(collectiviteId, membreId, membreUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MembresApi#updateMembre");
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
| **membreUpdate** | [**MembreUpdate**](MembreUpdate.md)|  | |

### Return type

[**Membre**](Membre.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Membre mis à jour |  -  |
| **404** | Ressource introuvable |  -  |

