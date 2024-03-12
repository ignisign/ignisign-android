# ignisign-android


Main class in the ignisign module: `ignisign/java/com/ignisign` 

To instantiate an ignisign webview: 

1. Add an IgnisignAndroid view in the layout of the activity or fragment concerned
2. Define an instance of IgnisignSignatureSessionDimensions
3. Define an instance of IgnisignJSSignatureSessionsDisplayOptions
4. Define an instance of IgnisignInitParams
5. Call setValues
6. Call initSignatureSession
7. The calling activity or fragment must implement ISessionCallbacks to receive events.

Retrieving contracts: - IgnisignAPI class, which contains the Retrofit interface to define the route and the IgnisignAPIService that fetches the contracts.

The old structure with the navigation drawer and fragments to retrieve sellers and customers is still present. 
To remove them, simply delete the associated kt and xml files; they are no longer used.

If there is a need to add views (activities) to the test project, they must be declared in AndroidManifest.XML, otherwise, it will not work.

To test contract retrieval via simulator, the equivalent of localhost is 10.0.0.2.

