from github import Github
import re
from recognizeUrls import is_pull
import sys
import time

# Authenticate with GitHub API using personal access token
#f = open("token.txt", 'r')
#token = f.readline()
#f.close()

#g = Github(token)
# Get the VS Code repository object
#repo = g.get_repo("elastic/elasticsearch")

# Get all the issues in the repository
#issues = repo.get_issues(state="all") #open, all
#print("1:", issues.totalCount)
#print("2:", len(list(issues)))
#exit()
#commits = repo.get_commits()

def recognize_issue_numbers_from_commit(text):
    issue_numbers = re.findall(r"#(\d+)", text)
    return issue_numbers

def print_commit_links(commit, repo):
    issue_numbers = recognize_issue_numbers_from_commit(commit.commit.message)
    for issue_number in issue_numbers:
        issue = repo.get_issue(int(issue_number))
        # Recognize the hash code in the text
        if issue is not None:
            bPull = is_pull(issue)
            files = commit.files
            for file in files:
                print(str(bPull) + ", " + str(issue_number) + ", " + "https://github.com/elastic/elasticsearch/commit/" + str(commit.sha) + ", " + str(file.filename))
            # fixed 된 url을 향후 수정해야 함


# Authenticate with GitHub API using personal access token
#g = Github("token")

# Get the VS Code repository object
#repo = g.get_repo("elastic/elasticsearch")

# Get all the commits in the repository
#commits = repo.get_commits()

# Iterate through the commits and print their SHA and commit message
#for commit in commits:
#   print_commit_links(commit, repo)
#   break
