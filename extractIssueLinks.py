from github import Github
from recognizeUrls import recognize_hash_code, recognize_web_address, recognize_web_addresses, is_pull, recognize_issue_numbers

# Authenticate with GitHub API using personal access token
#f = open("token.txt", 'r')
#token = f.readline()
#f.close()

#g = Github("token")

# Get the VS Code repository object
#repo = g.get_repo("elastic/elasticsearch")

def print_issue_links(issue_num, repo):
    # Get all the issues in the repository
    issue = repo.get_issue(issue_num)
        # Recognize the hash code in the text
    bPull = is_pull(issue)
    if bPull:
        issue_numbers = recognize_issue_numbers(issue)
        for issue_number in issue_numbers:
            print(str(bPull) + ", " + str(issue.number) + ", " + "https://github.com/elastic/elasticsearch/issues/" + str(issue_number))
        # TBD: 향후 프로젝트 specific 한 부분을 수정해야 함

    # print(issue.html_url)
    if issue.body is not None:
        hash_code = recognize_hash_code(issue.body)
        if hash_code is not None:
            print(str(bPull) + ", " + str(issue.number) + ", " + repo.html_url + "/commit/" + hash_code)
        web_address = recognize_web_address(issue.body)
        if web_address is not None:
            print(str(bPull) + ", " + str(issue.number) + ", " + web_address)

    # Recognize the web address in the text
    comments = issue.get_comments()
    for comment in comments:
        web_addresses = recognize_web_addresses(comment.body)
        if web_addresses is not None:
            for web_address in web_addresses:
                if web_address.endswith("!"):
                    web_address = web_address[:-1] 
                if web_address.endswith(","):
                    web_address = web_address[:-1] 
                if web_address.endswith("."):
                    web_address = web_address[:-1] 
                if web_address.endswith(")"):
                    web_address = web_address[:-1] 
                print(str(bPull) + ", " + str(issue.number) + ", " + web_address)

# print_issue_links(179216)
