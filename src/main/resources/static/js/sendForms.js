function submitFormEmEndpoint(endpoint, form){
    form.action = endpoint;
    form.submit();
}