from github import Github

# Authenticate with GitHub API using personal access token
f = open("token.txt", 'r')
token = f.readline()
f.close()

g = Github(token)

# Get the VS Code repository object
repo = g.get_repo("elastic/elasticsearch")

# Get all the commits in the repository
commits = repo.get_commits()

# Iterate through the commits and print their SHA and commit message
for commit in commits:
    files = commit.files
    for file in files:
        print(f"{commit.sha}, {file.filename}")    
    break
