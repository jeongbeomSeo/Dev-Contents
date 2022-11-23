# git version control

## 버전 관리란?
**버전 관리**란 파일 변화를 시간에 따라 기록했다가 나중에 특정 시점의 버전을 다시 꺼내올 수 있는 시스템이다.

 git은 소스코드를 효과적으로 관리하기 위해 개발된 '분산형 버전 관리 시스템'이다.


## git의 구조
git은 **Working directory**(.git이 들어가있는 폴더) 와 **Remote Repository**로 이루어져있다.

+ .git은 directory로서 GIT의 저장소입니다.

working diretory안을 살펴보면 ***working tree***, ***staging area***, ***local repository***로 나눠진다.

+ working tree: 파일을 만들고 작업, 수정하는 공간으로 파일이 version이 되기 전 단계이다.
+ staging area: 파일들을 version으로 만들려고 할 때, 만들려고 하는 file을 올려놔주는 공간이다.
+ local repository: version이 저장되어 있는 공간이다.


## 사용되는 용어 정리
| 분류 | 명령어 | 내용 설명 |
| ----- | --- | --- |
| <새로운 저장소 생성> | $ git init | Initalize Repository |
| <추가 및 확정(commit)> | $ git add * <br> $ git add <파일명> | Add to Staging Area |
| | $ git commit -option [MESSAGE] | Create Version |
| | $ git status | Working tree Status |
| <커밋 기록 보기> | $ git log | show version |
| <버전 삭제> | $ git reset --hard [MESSAGE] | 최종 version 이전의 상태로 
| <변경 사항 확인> | $ git diff <브랜치이름><다른 브랜치이름> | 변경 내용 병합(merge)전에 바뀐 내용을 비교할 수 있음 |
| <로컬 변경사항 return 작업> | $ git checkout --<파일명> | 로컬의 변경 사항을 변경 전으로 되돌림 |

## 구체적 활용법

### **1. commit 과정**

version 관리를 들어가고 싶은 directory에 이와 같이 명령어를 입력하면 된다.

    git init

이후 해당 directory에는 .git가 생성되고 이것은 version들의 정보를 저장하고 있는 git repository이다.

    git status

이 코드를 통해서 **version(commit)** 을 확인할 수 있고, **Untracked file**, **changeds to be commited** 확인 가능

> **Untracked file**을 설명하자면 Git은 자기 폴더에 있는 파일을 크게 Tracked, Untracked 이 두 가지 상태로 분류하여 관리한다.
>+  **Tracked file** : Git이 관리해주는 상태
>+  **Untracked file** : Git이 관리하지 않는 상태
  
git repository에서 version관리 하고 싶은 파일을 추가하려고 할 때 사용하는 명령어는 아래와 같다.

    git add [fileName]
    git add [directory]

이 명령어는 원하는 file혹은 directory에 있는 모든 file들을 **Staging Area**에 넣어주는 것이다.

이후 **commit**이라는 작업을 해주는데, **commit**이란 변화에 대한 기록이다.
    
    git commit -m [MESSAGE]
    git commit -am [MESSAGE]

### **2. version 관리**

**2.1 git log**

저장소의 history를 보고 싶을 경우, 아래의 명령어를 사용하면 된다.

    git log

지금까지의 커밋 기록을 쭉 출력해주며, 가장 위에 나오는 내역이 가장 최근 내역이다.

git log [옵션]의 경우, 조건을 걸어서 보고싶은 것들만 출력 하는 방식으로 대표적인 것들만 아래에 기록했다.

    git log --stat
    git log -p -[NUMBER]

+   **--stat**의 경우 각 커밋의 통게를 볼 수 있는데, 각 통계는 얼마나 많은 파일이 몇 줄이나 수정되거나 추가,  삭제되었는지를 보여주며 마지막에 요약 정보를 보여준다.

+   **-p**의 경우 각 커밋의 diff결과 즉, 변경 사항을 보여준다.
+   **-[NUNMBER]** 의 경우 최근 몇개의 내역을 보여줄 것인지 지정해준다. 

***tip) git log는 수정 사항을 확인함으로써, 나중에 복잡한 코드에서 오류 발생시 코드를 추적해가는데 유용한 방법으로 사용된다.***

**2.2 git diff**

**git diff**명령어의 경우, 파일의 어떤 내용이 변경되어있는지 차이점을 비교할 수 있다.

    git diff 

의 경우, commmit된 파일상태와 현재 수정중인 상태 비교

    git diff --staged

의 경우, commit된 파일상태와 add된 파일 상태 비교

이외에도, -HEAD를 이용하거나 -commmit hash를 이용하여 비교 가능하다.

**2.3 git reset || git revet**

***git reset***의 경우 커밋을 아예 취소하거나, ***git revet***는 커밋 내용을 되돌리는 방식이다.

    git reset --hard

위의 명령어의 경우, 마지막 version 이전의 상태로 돌아간다.

    git reset --hard [commit ID]

의 경우, commit ID가 가르키는 version으로 돌아간다는 것을 의미한다.

***cf) reset이 커밋을 취소한다는 것은, reset --hard의 경우 수정하고 있던 file과 version 둘다 지워버리기 때문이다.***

***git revert [commmit ID]*** 의 경우, commit ID이전의 version으로 되돌아 가는데 돌아가는 과정에서 Revert "{ID의 commit}"이라는 내용이 commmit되면서 되돌아 간다.

>**단, 주의해야 될 점은 reset이랑 다르게 revert는 몇 개의 commit을 건너서 되돌릴 수 없다.**
>
>1 -> 2 -> 3 -> 4(HEAD -> MASTER)인 상태에서 **git revert 4**로 해서 3으로 되돌아갈 수는 있지만 1로 돌아가고 싶다고 해서 **git revert 2**를 하면 오류가 난다.
>
>   >why? **git revet 2**는 [MESSAGE 2]이후의 모든 변화를 되돌리는 것이 아니라 [MESSAGE 2]를 할 때의 변화만을 되돌리는 것이므로 그 이후 생긴 변화를 git입장에서는 처리하지 못한다.