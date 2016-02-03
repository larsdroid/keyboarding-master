#!/bin/bash

if [ $# -lt 4 ] || [ -z $1  -o  -z $2  -o  -z $3  -o  -z $4 ]; then
    cat <<-EOF
    [!] error: missing arguments :(
    
     syntax: upload-to-bitbucket.sh <user> <password> <repo downloads page> <local file to upload>
    example: upload-to-bitbucket.sh swyter secret1 /Swyter/tld-downloads/downloads myfile.zip
EOF
    
    exit
fi
    usr=$1; pwd=$2; pge=$3; fil=$4
    
    # works like this: GET /account/signin/ -> POST /account/signin/ -> auto-redir to downloads page -> POST downloads page
    
    # GET initial csrf, dropped in the cookie, final 32 chars of the line containing that word
    # [i] note: you can add the "-v" parameter to any cURL command to get a detailed/verbose output, useful to diagnose problems.
    echo "getting initial csrf token from the sign-in page:"
    curl -k -c cookies.txt --progress-bar -o /dev/null https://bitbucket.org/account/signin/
    
    csrf=$(grep csrf cookies.txt); set $csrf; csrf=$7;
    
    # and login using POST, to get the final session cookies, then redirect it to the right page
    echo "signing in with the credentials provided:"
    curl -k -c cookies.txt -b cookies.txt --progress-bar -o /dev/null -d "username=$usr&password=$pwd&submit=&next=$pge&csrfmiddlewaretoken=$csrf" --referer "https://bitbucket.org/account/signin/" -L https://bitbucket.org/account/signin/
    
    csrf=$(grep csrf cookies.txt); set $csrf; csrf=$7;
    
    # check that we have the session cookie, if not, something bad happened, don't spend time uploading.

if [ -z "$(grep bb_session cookies.txt)" ]; then
    cat <<-EOF
    
    [!] error: didn't get the session cookie, probably bad credentials or they changed stuff... upload canceled!
EOF

    exit
fi

    # now that we're logged-in and at the right page, upload whatever you want to your repository...
    echo "actual upload progress should appear right now as a progress bar, be patient:"
    curl -k -c cookies.txt -b cookies.txt --progress-bar -o /dev/null --referer "https://bitbucket.org/$pge" -L --form csrfmiddlewaretoken=$csrf --form token= --form files=@"$fil" https://bitbucket.org/$pge

    echo "done? maybe. *crosses fingers* signing out, closing session!"
    curl -k -c cookies.txt -b cookies.txt --progress-bar -o /dev/null -L https://bitbucket.org/account/signout/
